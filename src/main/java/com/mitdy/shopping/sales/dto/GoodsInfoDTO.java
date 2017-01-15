package com.mitdy.shopping.sales.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsInfoDTO implements Serializable {

    private static final long serialVersionUID = -5760802912743381580L;

    private Long goodsId;
    private String goodsName;
    private String goodsDesc;
    private BigDecimal goodsUnitPrice;
    private BigDecimal discountPercentage;

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

    public BigDecimal getGoodsUnitPrice() {
        return goodsUnitPrice;
    }

    public void setGoodsUnitPrice(BigDecimal goodsUnitPrice) {
        this.goodsUnitPrice = goodsUnitPrice;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return "GoodsInfoDTO [goodsId=" + goodsId + ", goodsName=" + goodsName + ", goodsDesc=" + goodsDesc
                + ", goodsUnitPrice=" + goodsUnitPrice + ", discountPercentage=" + discountPercentage + "]";
    }

}
