package com.mitdy.shopping.goods.domain.enumeration;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mitdy.core.domain.AuditableEntity;

@Entity
@Table(name = "GOODS_GOODS")
public class Goods extends AuditableEntity {

    private static final long serialVersionUID = 4007631963531081973L;

}
