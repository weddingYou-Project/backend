package com.mysite.weddingyou_backend.payment;

public class PaymentCallbackRequest {
	
    private Long paymentId;
    private PaymentStatus status;
    private String paymentType;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
    
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
    
    public enum PaymentStatus {
        PAID("paid"),
        CANCELLED("cancelled"),
        OTHER("other");

        private final String value;

        PaymentStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

	public PaymentStatus getStatus() {
		return status;
	}
}
