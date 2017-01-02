package com.mitdy.shopping.sales.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitdy.shopping.sales.domain.SalesActivityItem;
import com.mitdy.shopping.sales.persistence.SalesActivityItemDao;
import com.mitdy.shopping.sales.service.SalesActivityService;

@Transactional
@Service("salesActivityService")
public class SalesActivityServiceImpl implements SalesActivityService {

    @Autowired
    private SalesActivityItemDao salesActivityItemDao;
    
    @Override
    public SalesActivityItem saveSalesActivityItem(SalesActivityItem item) {
        return salesActivityItemDao.save(item);
    }

    @Transactional(readOnly = true)
    @Override
    public SalesActivityItem findSalesActivityItemById(Long itemId) {
       return salesActivityItemDao.findById(itemId);
    }

}
