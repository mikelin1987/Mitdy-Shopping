package com.mitdy.core.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.mitdy.core.domain.AbstractEntity;
import com.mitdy.core.value.CommonCriteria;

/**
 * 操作数据库的抽象接口的直接实现，使用JPA技术
 * 
 * @author Mike
 * 
 * @param <Entity>参数类型名
 */
@Transactional
public class JpaAbstractEntityDao<T extends AbstractEntity> implements AbstractEntityDao<T> {

    /**
     * 用于操作实体的实体管理器
     */
    @PersistenceContext
    protected EntityManager em;

    /**
     * 实体的Class类型，运行时，取得操作数据库的实体的真实类型
     */
    protected Class<T> entityType;

    /**
     * 在构造函数中确定实现类中参数化的Class类型
     */
    @SuppressWarnings("unchecked")
    public JpaAbstractEntityDao() {
        entityType = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public T save(T entity) {
        entity =  em.merge(entity);
        em.flush();
        return entity;
    }

    @Override
    public void delete(Long id) {
        em.remove(findById(id));
        em.flush();
    }

    @Override
    public void delete(T entity) {
        delete(((AbstractEntity) entity).getId());
    }

    @Override
    public void deleteAll(List<T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }
    
    @Override
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            delete(id);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findAll() {
        return findWith(null, null);
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findWithSubList(int beginIndex, int length) {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("select entity from ").append(entityType.getSimpleName()).append(" entity");
        return em.createQuery(queryBuffer.toString()).setFirstResult(beginIndex).setMaxResults(length).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findWithSubList(int beginIndex, int length, Map<String, String> sortFieldAndOrder,
            Map<String, String> filters) {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("select entity from ").append(entityType.getSimpleName()).append(" entity");

        if (filters != null) {
            int index = 0;
            for (Entry<String, String> entry : filters.entrySet()) {

                if (index == 0) {
                    queryBuffer.append(" where ");
                } else {
                    queryBuffer.append(" and ");
                }

                onFilter(queryBuffer, entry);

                index += 1;
            }
        }
        
        orderBy(sortFieldAndOrder, queryBuffer);

        return em.createQuery(queryBuffer.toString()).setFirstResult(beginIndex).setMaxResults(length).getResultList();
    }

	protected void onFilter(StringBuffer queryBuffer, Entry<String, String> entry) {
		queryBuffer.append(entry.getKey()).append(" like '").append(entry.getValue()).append("%'");
	}

    protected void orderBy(Map<String, String> sortFieldAndOrder, StringBuffer queryBuffer) {
    	if (sortFieldAndOrder != null) {
            int index = 0;
            for (Entry<String, String> entry : sortFieldAndOrder.entrySet()) {

                if (index == 0) {
                    queryBuffer.append(" order by ");
                } else {
                    queryBuffer.append(" , ");
                }

                queryBuffer.append(entry.getKey()).append(" ").append(entry.getValue().toLowerCase());

                index += 1;
            }
        }
	}

	@Override
    public Long getTotalQuantity(Map<String, String> sortFieldAndOrder, Map<String, String> filters) {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("select count(*) from ").append(entityType.getSimpleName()).append(" entity");

        if (filters != null) {
            int index = 0;
            for (Entry<String, String> entry : filters.entrySet()) {

                if (index == 0) {
                    queryBuffer.append(" where ");
                } else {
                    queryBuffer.append(" and ");
                }

                onFilter(queryBuffer, entry);

                index += 1;
            }
        }
        orderBy(sortFieldAndOrder, queryBuffer);

        return (Long) em.createQuery(queryBuffer.toString()).getSingleResult();
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findByPage(int pageNumber, int pageSize) {
        return findWithSubList(pageSize * (pageNumber - 1), pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getTotalQuantity() {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("select count(*) from ").append(entityType.getSimpleName());
        return (Long) em.createQuery(queryBuffer.toString()).getSingleResult();
    }

    @Transactional(readOnly = true)
    @Override
    public Long findMaxId() {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("select max(entity.id) from ").append(entityType.getSimpleName()).append(" entity");
        try {
            Object result = em.createQuery(queryBuffer.toString()).getSingleResult();
            return result == null ? 0 : (Long) result;
        } catch (Exception e) {
            return 0L;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public T findById(Long id) {
        return em.find(entityType, id);
    }

    @Override
    public List<T> saveAll(List<T> entities) {
        List<T> newBeans = new ArrayList<T>();
        for (T entity : entities) {
            newBeans.add(save(entity));
        }
        return newBeans;
    }

    @Transactional(readOnly = true)
    @Override
    public T findUnique(String criterias, Map<String, Object> parameters) {
        List<T> entities = findWith(criterias, parameters);
        return entities != null && entities.size() == 1 ? entities.get(0) : null;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public T findDetailById(Long id, List<String> details) {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("select distinct entity from ").append(entityType.getSimpleName()).append(" entity ");

        for (String detail : details) {
            queryBuffer.append("left join fetch entity.").append(detail).append(" ");
        }
        queryBuffer.append(" where entity.id = :id");
        try {
            return (T) em.createQuery(queryBuffer.toString()).setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Sql syntax error exception");
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<T> findWith(String criterias, Map<String, Object> parameters) {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("select entity from ").append(entityType.getSimpleName()).append(" entity");
        Query query = null;
        if (parameters != null) {
            Set<Entry<String, Object>> parametersSet = parameters.entrySet();
            queryBuffer.append(" where ").append(criterias);
            query = em.createQuery(queryBuffer.toString());
            for (Entry<String, Object> entry : parametersSet) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        } else {
            query = em.createQuery(queryBuffer.toString());
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<T> findByIds(List<Long> ids) {
        if (ids == null) {
            ids = new ArrayList<Long>();
        }
        if (ids.isEmpty()) {
            ids.add(-1L);
        }

        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("select entity from ").append(entityType.getSimpleName()).append(" entity")
                .append(" where id in :ids ");
        return em.createQuery(queryBuffer.toString()).setParameter("ids", ids).getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<T> findByCriteria(CommonCriteria _criteria) {
        StringBuffer querySql = new StringBuffer("select e from ").append(entityType.getSimpleName()).append(" e ");

        Query query = appendQuery(_criteria, querySql);

        return query.setFirstResult(_criteria.getFirst()).setMaxResults(_criteria.getPageSize()).getResultList();
    }

	@Override
    public Long getCountByCriteria(CommonCriteria _criteria) {
    	StringBuffer querySql = new StringBuffer("select count(*) from ").append(entityType.getSimpleName()).append(" e ");

        Query query = appendQuery(_criteria, querySql);

        return (Long) query.getSingleResult();
    }
	
	protected Query appendQuery(CommonCriteria _criteria, StringBuffer querySql) {
		setParamNames(_criteria, querySql);
		
//		querySql.append(" order by e.id desc ");
		
		Query query = em.createQuery(querySql.toString());
		
		setParamValues(query, _criteria, querySql);
		
		return query;
	}

	protected void setParamNames(CommonCriteria _criteria, StringBuffer querySql) {
	}
	
	protected void setParamValues(Query query, CommonCriteria _criteria, StringBuffer querySql) {
	}

}
