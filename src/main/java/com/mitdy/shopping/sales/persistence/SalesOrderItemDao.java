package com.mitdy.shopping.sales.persistence;

import com.mitdy.core.persistence.AbstractEntityDao;
import com.mitdy.shopping.sales.domain.SalesOrderItem;

public interface SalesOrderItemDao extends AbstractEntityDao<SalesOrderItem> {

	public long countOrderItemByMember(Long activityItemId, Long memberId);
	
}
