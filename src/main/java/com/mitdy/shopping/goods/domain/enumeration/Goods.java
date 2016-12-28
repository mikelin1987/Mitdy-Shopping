package com.mitdy.shopping.goods.domain.enumeration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mitdy.core.domain.AuditableEntity;

@Entity
@Table(name = "GOODS_GOODS")
public class Goods extends AuditableEntity {

    private static final long serialVersionUID = 4007631963531081973L;

    @Column(name = "GOODS_NAME", length = 100, nullable = false)
    private String goodsName;

    @Column(name = "GOODS_DESC", length = 256, nullable = false)
    private String goodsDesc;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

}
