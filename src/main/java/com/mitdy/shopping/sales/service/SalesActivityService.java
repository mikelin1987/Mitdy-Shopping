package com.mitdy.shopping.sales.service;

import org.springframework.cache.annotation.Cacheable;

import com.mitdy.shopping.sales.domain.SalesActivityItem;
import com.mitdy.shopping.sales.dto.GoodsInfoDTO;

public interface SalesActivityService {

    public SalesActivityItem saveSalesActivityItem(SalesActivityItem item);

    public SalesActivityItem findSalesActivityItemById(Long itemId);

    public int increaseActivityItemSellCount(Long itemId, int count);

    @Cacheable(value = "goods", key = "'itemId_'+#itemId")
    public GoodsInfoDTO findGoodsInfoMapByItemId(Long itemId);

}
