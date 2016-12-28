package com.mitdy.shopping.sales.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mitdy.core.domain.AuditableEntity;

@Entity
@Table(name = "SALES_SALES_ORDER_ITEM")
public class SalesOrderItem extends AuditableEntity {

    private static final long serialVersionUID = 2987707526012176467L;

    private SalesOrder order;

    private String goodsId;

    private String goodsName;

    private String goodsDesc;

    private BigDecimal unitPrice;

    private BigDecimal actualUnitPrice;

    private BigDecimal totalAmount;

    public SalesOrder getOrder() {
        return order;
    }

    public void setOrder(SalesOrder order) {
        this.order = order;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
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
