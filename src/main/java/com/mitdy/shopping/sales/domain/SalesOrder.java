package com.mitdy.shopping.sales.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mitdy.core.domain.AuditableEntity;
import com.mitdy.shopping.sales.enumeration.SalesOrderStatus;

@Entity
@Table(name = "SALES_SALES_ORDER")
public class SalesOrder extends AuditableEntity {

	private static final long serialVersionUID = 6851163021563464098L;

	@Column(name = "ORDER_NO", length = 100, nullable = false)
	private String orderNo;

	@Column(name = "MEMBER_ID", nullable = false)
	private Long memberId;

	@Column(name = "PAYER_NAME", length = 20, nullable = false)
	private String payerName;

	@Column(name = "CONTACT_NO", length = 20, nullable = true)
	private String contactNo;

	@Column(name = "PAYMENT_TYPE", length = 50, nullable = true)
	private String paymentType;

	@Enumerated(EnumType.STRING)
	@Column(name = "ORDER_STATUS", length = 20, nullable = false)
	private SalesOrderStatus orderStatus;

	@Column(name = "ORDER_AMOUNT", precision = 10, scale = 2, length = 100, nullable = false)
	private BigDecimal orderAmount;

	@Column(name = "DELIVER_AMOUNT", precision = 10, scale = 2, length = 100, nullable = false)
	private BigDecimal deliverAmount;

	@Column(name = "DISCOUNT_AMOUNT", precision = 10, scale = 2, length = 100, nullable = false)
	private BigDecimal discountAmount;

	@Column(name = "ACTUAL_AMOUNT", precision = 10, scale = 2, length = 100, nullable = false)
	private BigDecimal actualAmount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SUBMIT_TIME", nullable = false)
	private Date submitTime;

	public SalesOrder() {
		this.orderStatus = SalesOrderStatus.PENDING;
	}

	public SalesOrder(String orderNo, Long memberId) {
		super();
		this.orderNo = orderNo;
		this.memberId = memberId;
	}

	public SalesOrder(String createUser) {
		super(createUser);
		// TODO Auto-generated constructor stub
	}

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

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

}
