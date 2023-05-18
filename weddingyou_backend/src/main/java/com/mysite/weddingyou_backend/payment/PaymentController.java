package com.mysite.weddingyou_backend.payment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        String status = callbackRequest.getStatus();
        
        //결제 상태에 따른 처리
        if (status.equals("paid")) {
            // 결제 완료 처리
            processPaymentComplete(paymentId);
        } else if (status.equals("cancelled")) {
            // 결제 취소 처리
            processPaymentCancel(paymentId);
        } else {
            // 기타 상태 처리
            processPaymentOther(paymentId);
        }
    }

    private void processPaymentComplete(Long paymentId) {
        // 결제 완료 처리 로직
        Payment payment = paymentService.getPaymentById(paymentId);
        payment.setPaymentStatus("completed");
        payment.setPaymentDate(LocalDateTime.now());
        paymentService.savePayment(payment);
    }

    private void processPaymentCancel(Long paymentId) {
        // 결제 취소 처리 로직
    	Payment payment = paymentService.getPaymentById(paymentId);
        payment.setPaymentStatus("cancelled");
        payment.setPaymentDate(LocalDateTime.now());
        paymentService.savePayment(payment);
    }

    private void processPaymentOther(Long paymentId) {
        // 기타 상태 처리 로직
        Payment payment = paymentService.getPaymentById(paymentId);
        payment.setPaymentStatus("other");
        payment.setPaymentDate(LocalDateTime.now());
        paymentService.savePayment(payment);
    }
}
