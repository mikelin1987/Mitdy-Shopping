package com.mitdy.shopping.sales.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mitdy.core.domain.AuditableEntity;

@Entity
@Table(name = "SALES_PAYMENT_TYPE")
public class PaymentType extends AuditableEntity {

    private static final long serialVersionUID = -2568395916678644911L;

    private String paymentName;

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

}
