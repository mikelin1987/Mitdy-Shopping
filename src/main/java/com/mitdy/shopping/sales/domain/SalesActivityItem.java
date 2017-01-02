package com.mitdy.shopping.sales.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mitdy.core.domain.AuditableEntity;
import com.mitdy.shopping.sales.enumeration.SalesActivityStatus;

@Entity
@Table(name = "SALES_SALES_ACTIVITY_ITEM")
public class SalesActivityItem extends AuditableEntity {

    private static final long serialVersionUID = -474539158394207528L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACTIVITY_ID")
    private SalesActivity activity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACTIVITY_CONDUCTOR_ID")
    private SalesActivityConductor activityConductor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GOODS_PRICING_ID")
    private GoodsPricing goodsPricing;

    @Column(name = "DISCOUNT_PERCENTAGE", precision = 2, scale = 2, length = 100, nullable = true)
    private BigDecimal discountPercentage;

    @Column(name = "DISCOUNT_AMOUNT", precision = 10, scale = 2, length = 100, nullable = true)
    private BigDecimal discountAmount;

    @Column(name = "SECONDS_KILL_COUNT", nullable = true)
    private int secondsKillCount;

    @Column(name = "SELL_COUNT", nullable = false)
    private int sellCount;

    @Column(name = "LIMITATION_COUNT_PER_MEMBER", nullable = false)
    private int limitationCountPerMember;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFFECTIVE_START_TIME", nullable = false)
    private Date effectiveStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFFECTIVE_END_TIME", nullable = false)
    private Date effectiveEndTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTIVITY_STATUS", length = 20, nullable = false)
    private SalesActivityStatus activityStatus;
    
    public boolean isSellOut() {
    	return (sellCount >= secondsKillCount);
    }

    public SalesActivity getActivity() {
        return activity;
    }

    public void setActivity(SalesActivity activity) {
        this.activity = activity;
    }

    public SalesActivityConductor getActivityConductor() {
        return activityConductor;
    }

    public void setActivityConductor(SalesActivityConductor activityConductor) {
        this.activityConductor = activityConductor;
    }

    public GoodsPricing getGoodsPricing() {
        return goodsPricing;
    }

    public void setGoodsPricing(GoodsPricing goodsPricing) {
        this.goodsPricing = goodsPricing;
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

    public int getSecondsKillCount() {
        return secondsKillCount;
    }

    public void setSecondsKillCount(int secondsKillCount) {
        this.secondsKillCount = secondsKillCount;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public int getLimitationCountPerMember() {
        return limitationCountPerMember;
    }

    public void setLimitationCountPerMember(int limitationCountPerMember) {
        this.limitationCountPerMember = limitationCountPerMember;
    }

    public Date getEffectiveStartTime() {
        return effectiveStartTime;
    }

    public void setEffectiveStartTime(Date effectiveStartTime) {
        this.effectiveStartTime = effectiveStartTime;
    }

    public Date getEffectiveEndTime() {
        return effectiveEndTime;
    }

    public void setEffectiveEndTime(Date effectiveEndTime) {
        this.effectiveEndTime = effectiveEndTime;
    }

    public SalesActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(SalesActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }

}
