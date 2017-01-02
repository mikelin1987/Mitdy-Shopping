package com.mitdy.shopping.sales.persistence.impl;

import org.springframework.stereotype.Repository;

import com.mitdy.core.persistence.JpaAbstractEntityDao;
import com.mitdy.shopping.sales.domain.SalesOrder;
import com.mitdy.shopping.sales.persistence.SalesOrderDao;

@Repository("salesOrderDao")
public class SalesOrderDaoImpl extends JpaAbstractEntityDao<SalesOrder> implements SalesOrderDao {

}
