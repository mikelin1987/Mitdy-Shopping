package com.mitdy.shopping.sales.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mitdy.core.persistence.JpaAbstractEntityDao;
import com.mitdy.shopping.sales.domain.SalesActivityItem;
import com.mitdy.shopping.sales.persistence.SalesActivityItemDao;

@Repository("salesActivityItemDao")
public class SalesActivityItemDaoImpl extends JpaAbstractEntityDao<SalesActivityItem> implements SalesActivityItemDao {

}
