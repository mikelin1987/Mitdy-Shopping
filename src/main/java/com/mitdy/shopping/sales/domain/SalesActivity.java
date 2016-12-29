package com.mitdy.shopping.sales.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mitdy.core.domain.AuditableEntity;

@Entity
@Table(name = "SALES_SALES_ACTIVITY")
public class SalesActivity extends AuditableEntity {

    private static final long serialVersionUID = -5270605367234825081L;

    @Column(name = "ACTIVITY_NAME", length = 100, nullable = true)
    private String activityname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACTIVITY_CONDUCTOR_ID")
    private SalesActivityConductor activityConductor;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SLAES_ACTIVITY_GOODS_PRICINGS", joinColumns = {
            @JoinColumn(name = "ACTIVITY_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
                    @JoinColumn(name = "GOODS_PRICING_ID", referencedColumnName = "ID") })
    private List<GoodsPricing> goodsPricings;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFFECTIVE_START_TIME", nullable = false)
    private Date effectiveStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFFECTIVE_END_TIME", nullable = false)
    private Date effectiveEndTime;

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }

    public SalesActivityConductor getActivityConductor() {
        return activityConductor;
    }

    public void setActivityConductor(SalesActivityConductor activityConductor) {
        this.activityConductor = activityConductor;
    }

    public List<GoodsPricing> getGoodsPricings() {
        return goodsPricings;
    }

    public void setGoodsPricings(List<GoodsPricing> goodsPricings) {
        this.goodsPricings = goodsPricings;
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

}
