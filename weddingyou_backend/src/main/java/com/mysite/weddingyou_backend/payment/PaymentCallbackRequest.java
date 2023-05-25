package com.mysite.weddingyou_backend.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentCallbackRequest {
	
	private Long paymentId;
	private BigDecimal price; //상품 가격
	private Integer quantity; //상품 수량
	private String paymentMethod; //결제 수단
	private BigDecimal paymentAmount; //결제 금액
    private PaymentStatus paymentStatus; //결제 상태
    private LocalDateTime paymentDate; //결제 일시
    private BigDecimal depositAmount; //계약금 금액
    private PaymentStatus depositStatus; //계약금 결제 상태
    private LocalDateTime depositDate; //계약금 결제 일시
    private String paymentType;
    private String userEmail;
    private String plannerEmail;
    private Long itemId;
    
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }
    
    public LocalDateTime getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(LocalDateTime depositDate) {
        this.depositDate = depositDate;
    }
    
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    
    public enum PaymentStatus {
        PAID("paid"), //결제 완료
        CANCELLED("cancelled"), //결제 취소
        OTHER("other"); //기타(결제 실패, 결제 보류중 등)

        private final String value;

        PaymentStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	
	public PaymentStatus getDepositStatus() {
		return depositStatus;
	}
	
	public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public String getPlannerEmail() {
        return plannerEmail;
    }

    public void setPlannerEmail(String plannerEmail) {
        this.plannerEmail = plannerEmail;
    }
    
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
