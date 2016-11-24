package com.mitdy.core.persistence;

import java.util.List;
import java.util.Map;

import com.mitdy.core.domain.AbstractEntity;
import com.mitdy.core.value.CommonCriteria;

/**
 * 操作数据库的抽象接口
 * 
 * @author Mike
 * 
 * @param <Entity>参数类型名
 */
public interface AbstractEntityDao<Entity extends AbstractEntity> {

	/**
	 * 新增或更新实体到数据库
	 * 
	 * @param entity
	 *            需要持久化的实体
	 * @return 返回已持久化状态的实体
	 */
	public Entity save(Entity entity);

	/**
	 * 新增或更新多个实体到数据库
	 * 
	 * @param entities
	 *            需要持久化的多个实体
	 * @return 返回已持久化状态的实体的列表
	 */
	public List<Entity> saveAll(List<Entity> entities);

	/**
	 * 通过实体从数据库删除实体对应的数据
	 * 
	 * @param entity
	 *            需要删除的实体
	 */
	public void delete(Entity entity);

	/**
	 * 通过实体id从数据库删除实体对应的数据
	 * 
	 * @param id
	 *            需要删除的实体id
	 */
	public void delete(Long id);
	
	public void delete(List<Long> ids);

	/**
	 * 批量删除实体对应的数据
	 * 
	 * @param entities
	 *            需要删除的批量实体
	 */
	public void deleteAll(List<Entity> entities);

	/**
	 * 从数据库查找对应id的实体
	 * 
	 * @param id
	 *            实体id
	 * @return 如果存在对应id的实体，则返回此实体；否则返回null
	 */
	public Entity findById(Long id);

	/**
	 * 从数据库查找所有的实体
	 * 
	 * @return 返回实体的列表
	 */
	public List<Entity> findAll();

	/**
	 * 从数据库中查找指定开始索引和指定长度的多个实体
	 * 
	 * @param beginIndex
	 *            开始索引
	 * @param length
	 *            实体列表的长度
	 * @return 返回查找到的实体的列表
	 */
	public List<Entity> findWithSubList(int beginIndex, int length);

	public List<Entity> findWithSubList(int beginIndex, int length, Map<String, String> sortFieldAndOrder,
			Map<String, String> filters);

	public Long getTotalQuantity(Map<String, String> sortFieldAndOrder, Map<String, String> filters);

	/**
	 * 从数据库中分布查找指定页码和指定数据量的多个实体
	 * 
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            个页中的数据数据量
	 * @return 返回查找到的实体的列表
	 */
	public List<Entity> findByPage(int pageNumber, int pageSize);

	/**
	 * 查找实体在数据库中的数量
	 * 
	 * @return 返回数量
	 */
	public Long getTotalQuantity();

	/**
	 * 查找在数据库中实体最大的id
	 * 
	 * @return
	 */
	public Long findMaxId();

	/**
	 * 根据查询语句和参数查找实体
	 * 
	 * @param criterias
	 *            带条件的查询语句
	 * @param parameters
	 *            参数名与参数值对
	 * @return 返回满足条件的实体的列表
	 */
	public List<Entity> findWith(String criterias, Map<String, Object> parameters);
	
	public List<Entity> findByIds(List<Long> ids);
	
	public List<Entity> findByCriteria(CommonCriteria _criteria);
	
	public Long getCountByCriteria(CommonCriteria _criteria);
	
	/**
	 * 根据查询语句和参数查找一个实体
	 * 
	 * @param criterias
	 *            带条件的查询语句
	 * @param parameters
	 *            参数名与参数值对
	 * @return 返回满足条件的实体
	 */
	public Entity findUnique(String criterias, Map<String, Object> parameters);

	/**
	 * 查找特定id，并加载相关数据的实体
	 * 
	 * @param id
	 *            实体id
	 * @param details
	 *            需要加载的属性，多个
	 * @return 返回已加载了特定属性数据的实体
	 */
	public Entity findDetailById(Long id, List<String> details);

}
