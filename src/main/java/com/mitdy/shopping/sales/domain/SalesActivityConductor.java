package com.mitdy.shopping.sales.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.mitdy.core.domain.AuditableEntity;
import com.mitdy.shopping.sales.enumeration.SalesActivityType;

@Entity
@Table(name = "SALES_SALES_ACTIVITY_CONDUCTOR")
public class SalesActivityConductor extends AuditableEntity {

    private static final long serialVersionUID = 1466010153610146907L;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTIVITY_TYPE", length = 20, nullable = false)
    private SalesActivityType activityType;

    @Column(name = "SECONDS_KILL_COUNT", nullable = true)
    private int secondsKillCount;

    @Column(name = "DISCOUNT_PERCENTAGE", precision = 2, scale = 2, length = 100, nullable = true)
    private BigDecimal discountPercentage;

    @Column(name = "DISCOUNT_AMOUNT", precision = 10, scale = 2, length = 100, nullable = true)
    private BigDecimal discountAmount;

    public SalesActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(SalesActivityType activityType) {
        this.activityType = activityType;
    }

    public int getSecondsKillCount() {
        return secondsKillCount;
    }

    public void setSecondsKillCount(int secondsKillCount) {
        this.secondsKillCount = secondsKillCount;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

}
