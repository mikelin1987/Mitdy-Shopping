package com.mitdy.shopping.sales.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mitdy.core.domain.AuditableEntity;

@Entity
@Table(name = "SALES_SALES_ORDER_ITEM")
public class SalesOrderItem extends AuditableEntity {

    private static final long serialVersionUID = 2987707526012176467L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private SalesOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SALES_ACTIVITY_ITEM_ID")
    private SalesActivityItem salesActivityItem;

    @Column(name = "GOODS_ID", nullable = false)
    private Long goodsId;

    @Column(name = "GOODS_NAME", length = 100, nullable = false)
    private String goodsName;

    @Column(name = "GOODS_DESC", length = 256, nullable = false)
    private String goodsDesc;

    @Column(name = "UNIT_PRICE", precision = 10, scale = 2, length = 100, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "ACTUAL_UNIT_PRICE", precision = 10, scale = 2, length = 100, nullable = false)
    private BigDecimal actualUnitPrice;

    @Column(name = "QUANTITY", precision = 10, scale = 2, length = 100, nullable = false)
    private BigDecimal quantity;

    @Column(name = "TOTAL_AMOUNT", precision = 10, scale = 2, length = 100, nullable = false)
    private BigDecimal totalAmount;

    public SalesOrder getOrder() {
        return order;
    }

    public void setOrder(SalesOrder order) {
        this.order = order;
    }

    public SalesActivityItem getSalesActivityItem() {
        return salesActivityItem;
    }

    public void setSalesActivityItem(SalesActivityItem salesActivityItem) {
        this.salesActivityItem = salesActivityItem;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getActualUnitPrice() {
        return actualUnitPrice;
    }

    public void setActualUnitPrice(BigDecimal actualUnitPrice) {
        this.actualUnitPrice = actualUnitPrice;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

}
