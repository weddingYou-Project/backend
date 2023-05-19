package com.mysite.weddingyou_backend.payment;

import java.time.LocalDateTime;

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
        this.api = new IamportClient("4682177181885536","ZLbvvl3cqH1SwCB549U1cZ2QVJc1lTSr7nhRnKaQX2Rt0wz79Ys2enLxfWhpuKvI4Ol0QNvj5lxMaKLx");
    }
    
    @PostMapping(value = "/payment/callback")
    public void handlePaymentCallback(@RequestBody PaymentCallbackRequest callbackRequest) {
        // 콜백 이벤트 처리 로직
    	Long paymentId = callbackRequest.getPaymentId();
        PaymentStatus paymentStatus = callbackRequest.getPaymentStatus();
        PaymentStatus depositStatus = callbackRequest.getDepositStatus();
        String paymentType = callbackRequest.getPaymentType(); // 결제 종류 추가
        
        System.out.println("paymentID 확인 : " + paymentId);
        
        
//        if (paymentType.equals("deposit")) {
//            // 계약금 결제 처리
//            processDepositPayment(paymentId, status.getValue());
//        } else if (paymentType.equals("all")){
//            // 전체 금액 결제 처리
//        	processPayment(paymentId, status.getValue());
//        }
    
        //계약금의 결제 상태가 paid가 아닐 경우 전체 금액 결제 처리 로직이 돌아가지 않도록 수정
        if (paymentType.equals("deposit")) {
        	processDepositPayment(paymentId, depositStatus.getValue());
        }else if (paymentType.equals("all") && depositStatus == PaymentStatus.PAID) {
        	processDepositPayment(paymentId, depositStatus.getValue());
    		processPayment(paymentId, paymentStatus.getValue());
    	}else {
    		System.out.println("계약금을 먼저 결제해주세요.");
    	}

    }
    
    private void processDepositPayment(Long paymentId, String depositStatus) {
        // 계약금 결제 처리 로직
        Payment payment = paymentService.getPaymentById(paymentId);
        payment.setDepositStatus(depositStatus);
        payment.setDepositDate(LocalDateTime.now());
        payment.setPaymentType("deposit"); // paymentType 업데이트
        paymentService.savePayment(payment);
    }

    private void processPayment(Long paymentId, String paymentStatus) {
        // 전체 금액 결제 처리 로직
        Payment payment = paymentService.getPaymentById(paymentId);
        payment.setPaymentStatus(paymentStatus);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentType("all"); // paymentType 업데이트
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
