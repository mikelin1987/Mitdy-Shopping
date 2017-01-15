package com.mitdy.shopping.sales.mapper;

import java.io.Serializable;
import java.util.Map;

import com.mitdy.shopping.sales.dto.GoodsInfoDTO;

public interface SalesActivityItemMapper  {

    public int increaseSellCount(Long itemId, int count);
    
    public GoodsInfoDTO findGoodsInfoMapByItemId(Long itemId);
    
    public Map<String, Serializable> findGoodsInfoMapByItemId2(Long itemId);
    
}
