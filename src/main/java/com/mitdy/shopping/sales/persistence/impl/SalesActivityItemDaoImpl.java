package com.mitdy.shopping.sales.persistence.impl;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.mitdy.core.persistence.JpaAbstractEntityDao;
import com.mitdy.shopping.sales.domain.SalesActivityItem;
import com.mitdy.shopping.sales.persistence.SalesActivityItemDao;

@Repository("salesActivityItemDao")
public class SalesActivityItemDaoImpl extends JpaAbstractEntityDao<SalesActivityItem> implements SalesActivityItemDao {

    @Override
    public SalesActivityItem findById(Long id) {
        try {
            return (SalesActivityItem) em.createQuery("select e from SalesActivityItem e where e.id = :id")
                    .setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
