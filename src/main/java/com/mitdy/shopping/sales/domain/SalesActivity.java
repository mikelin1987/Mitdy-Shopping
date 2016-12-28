package com.mitdy.shopping.sales.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mitdy.core.domain.AuditableEntity;

@Entity
@Table(name = "SALES_SALES_ACTIVITY")
public class SalesActivity extends AuditableEntity {

    private static final long serialVersionUID = -5270605367234825081L;

}
