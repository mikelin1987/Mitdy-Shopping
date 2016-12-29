package com.mitdy.shopping.sales.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mitdy.core.domain.AuditableEntity;

@Entity
@Table(name = "SALES_PAYMENT_TYPE")
public class PaymentType extends AuditableEntity {

    private static final long serialVersionUID = -2568395916678644911L;

    @Column(name = "PAYMENT_NAME", length = 50, nullable = false)
    private String paymentName;

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

}
