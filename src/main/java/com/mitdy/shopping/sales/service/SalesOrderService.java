package com.mitdy.shopping.sales.service;

import com.mitdy.shopping.sales.dto.CreateActivityOrderDTO;

public interface SalesOrderService {

	public void createActivityOrder(CreateActivityOrderDTO orderDTO);
	
	public void createActivityOrderByNativeSQL(CreateActivityOrderDTO orderDTO) throws Exception ;
	
}
