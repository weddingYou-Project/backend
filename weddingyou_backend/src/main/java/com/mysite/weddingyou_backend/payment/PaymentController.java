package com.mysite.weddingyou_backend.payment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.weddingyou_backend.payment.PaymentCallbackRequest.PaymentStatus;
import com.siot.IamportRestClient.IamportClient;


@RestController
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;
	
    private IamportClient api;

    public PaymentController() {
        this.api = new IamportClient("[복사했던 REST API키]","[복사했던 REST API secret]");
    }
    
    @PostMapping(value = "/payment/callback")
    public void handlePaymentCallback(@RequestBody PaymentCallbackRequest callbackRequest) {
        // 콜백 이벤트 처리 로직
    	Long paymentId = callbackRequest.getPaymentId();
        PaymentStatus status = callbackRequest.getStatus();
        String paymentType = callbackRequest.getPaymentType(); // 결제 종류 추가
        
        
        if (paymentType.equals("deposit")) {
            // 계약금 결제 처리
            processDepositPayment(paymentId, status.getValue());
        } else {
            // 일반 결제 처리
        	processPayment(paymentId, status.getValue());
        }
    }
    
    private void processDepositPayment(Long paymentId, String status) {
        // 계약금 결제 처리 로직
        Payment payment = paymentService.getPaymentById(paymentId);
        payment.setDepositStatus(status);
        payment.setDepositDate(LocalDateTime.now());
        paymentService.savePayment(payment);
    }

    private void processPayment(Long paymentId, String status) {
        // 결제 처리 로직
        Payment payment = paymentService.getPaymentById(paymentId);
        payment.setPaymentStatus(status);
        payment.setPaymentDate(LocalDateTime.now());
        paymentService.savePayment(payment);
    }

//    private void processPaymentCancel(Long paymentId) {
//        // 결제 취소 처리 로직
//    	Payment payment = paymentService.getPaymentById(paymentId);
//        payment.setPaymentStatus("cancelled");
//        payment.setPaymentDate(LocalDateTime.now());
//        paymentService.savePayment(payment);
//    }
//
//    private void processPaymentOther(Long paymentId) {
//        // 기타 상태 처리 로직
//        Payment payment = paymentService.getPaymentById(paymentId);
//        payment.setPaymentStatus("other");
//        payment.setPaymentDate(LocalDateTime.now());
//        paymentService.savePayment(payment);
//    }
}
