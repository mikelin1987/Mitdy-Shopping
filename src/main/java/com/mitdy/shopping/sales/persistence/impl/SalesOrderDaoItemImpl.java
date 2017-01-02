package com.mitdy.shopping.sales.persistence.impl;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.mitdy.core.persistence.JpaAbstractEntityDao;
import com.mitdy.shopping.sales.domain.SalesOrderItem;
import com.mitdy.shopping.sales.persistence.SalesOrderItemDao;

@Repository("salesOrderItemDao")
public class SalesOrderDaoItemImpl extends JpaAbstractEntityDao<SalesOrderItem> implements SalesOrderItemDao {

    @Override
    public long countOrderItemByMember(Long activityItemId, Long memberId) {
        try {
            Object singleResult = em
                    .createQuery(
                            "select count(*) from SalesOrderItem e where e.salesActivityItem.id = :activityItemId and e.order.memberId = :memberId")
                    .setParameter("activityItemId", activityItemId).setParameter("memberId", memberId)
                    .getSingleResult();
            return ((Long) singleResult).longValue();
        } catch (NoResultException e) {
            return 0L;
        }
    }

}
