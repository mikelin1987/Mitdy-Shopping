package com.mitdy.shopping.sales.dto;

import java.io.Serializable;

public class CreateActivityOrderDTO implements Serializable {

	private static final long serialVersionUID = 8094361619377753746L;

	private Long memberId;
	private Long activityItemId;
	private int quantity;

	private String payerName;
	private String contactNo;
	private String paymentType;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getActivityItemId() {
		return activityItemId;
	}

	public void setActivityItemId(Long activityItemId) {
		this.activityItemId = activityItemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

}
