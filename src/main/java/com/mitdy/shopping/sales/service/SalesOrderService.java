package com.mitdy.shopping.sales.service;

import com.mitdy.shopping.sales.dto.CreateActivityOrderDTO;

public interface SalesOrderService {

	public void createActivityOrderByHibernate(CreateActivityOrderDTO orderDTO);
	
	public void createActivityOrderByNativeSQL(CreateActivityOrderDTO orderDTO) throws Exception ;
	
	public void createActivityOrderByMyBatis(CreateActivityOrderDTO orderDTO);
	
}
