package com.mitdy.shopping.sales.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitdy.shopping.sales.domain.SalesActivityItem;
import com.mitdy.shopping.sales.dto.GoodsInfoDTO;
import com.mitdy.shopping.sales.mapper.SalesActivityItemMapper;
import com.mitdy.shopping.sales.persistence.SalesActivityItemDao;
import com.mitdy.shopping.sales.service.SalesActivityService;

@Transactional
@Service("salesActivityService")
public class SalesActivityServiceImpl implements SalesActivityService {

    public static final Logger logger = LoggerFactory.getLogger(SalesActivityServiceImpl.class);

    @Autowired
    private SalesActivityItemDao salesActivityItemDao;

    @Autowired
    private SalesActivityItemMapper salesActivityItemMapper;

    @Override
    public SalesActivityItem saveSalesActivityItem(SalesActivityItem item) {
        return salesActivityItemDao.save(item);
    }

    @Transactional(readOnly = true)
    @Override
    public SalesActivityItem findSalesActivityItemById(Long itemId) {
        return salesActivityItemDao.findById(itemId);
    }

    @Override
    public int increaseActivityItemSellCount(Long itemId, int count) {
        // return salesActivityItemDao.increaseSellCount(itemId, count);

        return salesActivityItemMapper.increaseSellCount(itemId, count);
    }

    @Override
    public GoodsInfoDTO findGoodsInfoMapByItemId(Long itemId) {
        logger.info("findGoodsInfoMapByItemId: {}", itemId);

        GoodsInfoDTO goodsInfoDTO = salesActivityItemMapper.findGoodsInfoMapByItemId(itemId);

        return goodsInfoDTO;
    }

}
