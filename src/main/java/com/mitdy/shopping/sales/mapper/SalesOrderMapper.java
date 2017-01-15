package com.mitdy.shopping.sales.mapper;

import com.mitdy.shopping.sales.dto.SalesOrderDTO;
import com.mitdy.shopping.sales.dto.SalesOrderItemDTO;

public interface SalesOrderMapper {
    
    public int insertSalesOrder(SalesOrderDTO order);
    
    public int insertSalesOrderItem(SalesOrderItemDTO orderItem);
    
}
