package com.mysite.weddingyou_backend.payment;

public class PaymentCallbackRequest {
	
    private Long paymentId;
    private PaymentStatus paymentStatus;
    private PaymentStatus depositStatus;
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
}
