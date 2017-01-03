package com.mitdy.shopping.sales.service;

import com.mitdy.shopping.sales.domain.SalesActivityItem;

public interface SalesActivityService {

    public SalesActivityItem saveSalesActivityItem(SalesActivityItem item);
    
    public SalesActivityItem findSalesActivityItemById(Long itemId);
    
    public int increaseActivityItemSellCount(Long itemId, int count);
    
}
