package com.mitdy.shopping.sales.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mitdy.core.domain.AuditableEntity;
import com.mitdy.shopping.sales.enumeration.SalesOrderStatus;

@Entity
@Table(name = "SALES_SALES_ORDER")
public class SalesOrder extends AuditableEntity {

    private static final long serialVersionUID = 6851163021563464098L;

    private String orderNo;

    private Long memberId;

    private String payerName;

    private String contactNo;

    private String paymentType;

    private SalesOrderStatus orderStatus;

    private BigDecimal orderAmount;

    private BigDecimal deliverAmount;

    private BigDecimal discountAmount;

    private BigDecimal actualAmount;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public SalesOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(SalesOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getDeliverAmount() {
        return deliverAmount;
    }

    public void setDeliverAmount(BigDecimal deliverAmount) {
        this.deliverAmount = deliverAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

}
