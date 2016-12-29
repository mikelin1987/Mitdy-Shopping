package com.mitdy.shopping.sales.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mitdy.core.domain.AuditableEntity;
import com.mitdy.shopping.goods.domain.enumeration.Goods;

@Entity
@Table(name = "SALES_GOODS_PRICING")
public class GoodsPricing extends AuditableEntity {

    private static final long serialVersionUID = 6096249409634819640L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GOODS_ID")
    private Goods goods;

    @Column(name = "UNIT_PRICE", precision = 10, scale = 2, length = 100, nullable = true)
    private BigDecimal unitPrice;

    @Column(name = "DISCOUNT_PERCENTAGE", precision = 2, scale = 2, length = 100, nullable = true)
    private BigDecimal discountPercentage;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

}
