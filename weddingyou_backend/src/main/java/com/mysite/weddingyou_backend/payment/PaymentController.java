package com.mysite.weddingyou_backend.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.weddingyou_backend.estimate.Estimate;
import com.mysite.weddingyou_backend.item.ItemRepository;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLogin;
import com.mysite.weddingyou_backend.plannerLogin.PlannerLoginRepository;
import com.mysite.weddingyou_backend.userLogin.UserLogin;
import com.mysite.weddingyou_backend.userLogin.UserLoginRepository;


@RestController
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	PlannerLoginRepository plannerLoginRepository;
	
	@Autowired
	UserLoginRepository userLoginRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
//    private IamportClient api;
//
//    public PaymentController() {
//        this.api = new IamportClient("4682177181885536","ZLbvvl3cqH1SwCB549U1cZ2QVJc1lTSr7nhRnKaQX2Rt0wz79Ys2enLxfWhpuKvI4Ol0QNvj5lxMaKLx");
//    }
    
    @PostMapping(value = "/deposit/callback")
    public int handlePaymentCallback(@RequestBody PaymentCallbackRequest callbackRequest) {
        // 콜백 이벤트 처리 로직
    	PaymentCallbackRequest temp = new PaymentCallbackRequest();
    	BigDecimal price = callbackRequest.getPrice();
    	Integer quantity = callbackRequest.getQuantity();
    	String paymentMethod = callbackRequest.getPaymentMethod();
    	BigDecimal paymentAmount = callbackRequest.getPaymentAmount();
    	callbackRequest.setPaymentStatus(callbackRequest.getTempPaymentStatus());
        String paymentStatus = callbackRequest.getPaymentStatus();
        BigDecimal depositAmount = callbackRequest.getDepositAmount();
        callbackRequest.setDepositStatus(callbackRequest.getTempDepositStatus());
        String depositStatus = callbackRequest.getDepositStatus();
        String paymentType = callbackRequest.getPaymentType();
        String userEmail = callbackRequest.getUserEmail(); // userEmail 추가
        String plannerEmail = callbackRequest.getPlannerEmail(); // plannerEmail 추가
        Long estimateId = callbackRequest.getEstimateId(); // estimateId 추가)
        System.out.println("estimateId:"+estimateId);
        
        //현재 시간 가져옴
        LocalDateTime currentTime = LocalDateTime.now();
        
        // 데이터베이스에서 Planner 정보 가져오기
        PlannerLogin planner = plannerLoginRepository.findByEmail(plannerEmail);
        String plannerName = planner.getName();
        String plannerImg = planner.getPlannerImg();
        
        // 데이터베이스에서 User 정보 가져오기
        UserLogin user = userLoginRepository.findByEmail(userEmail);
        
    	if(paymentService.getPaymentData(callbackRequest.getEstimateId())==null) {
    		
            // 데이터베이스에서 Item 정보 가져오기
         //   Optional<Item> item = itemRepository.findById(itemId);
            
            // 데이터베이스에 저장하기 위해 Payment 객체 생성
            Payment payment = new Payment();
            payment.setPrice(price);
            payment.setQuantity(quantity);
            payment.setPaymentMethod(paymentMethod);
            payment.setPaymentAmount(paymentAmount);
            payment.setPaymentStatus(paymentStatus);
            payment.setDepositAmount(depositAmount);
            payment.setDepositStatus(depositStatus);
            payment.setPaymentType(paymentType);
            payment.setUserEmail(userEmail);
            payment.setPlannerEmail(plannerEmail);
            payment.setEstimateId(estimateId);
        //    payment.setItemId(itemId);
            
            // 플래너 정보를 Payment 객체에 설정
            payment.setPlanner(planner);
            
            if (paymentType.equals("deposit")) {
                payment.setDepositDate(currentTime);
                
            } else {   
                payment.setPaymentDate(currentTime);
            }
            
            // Payment 객체를 데이터베이스에 저장
            paymentService.savePayment(payment);
    	}else { //estimateId가 겹칠 경우에 관련 데이터 업데이트
    		Payment targetPayment = paymentService.getPaymentData(callbackRequest.getEstimateId());
    		targetPayment.setPrice(price);
    		targetPayment.setQuantity(quantity);
    		targetPayment.setPaymentMethod(paymentMethod);
    		targetPayment.setPaymentAmount(paymentAmount);
    		targetPayment.setPaymentStatus(paymentStatus);
    		targetPayment.setDepositAmount(depositAmount);
    		targetPayment.setDepositStatus(depositStatus);
    		targetPayment.setPaymentType(paymentType);
    		targetPayment.setUserEmail(userEmail);
    		targetPayment.setPlannerEmail(plannerEmail);
    		targetPayment.setEstimateId(estimateId);
    		
    		targetPayment.setPlanner(planner);
    		
    		 if (paymentType.equals("deposit")) {
    			 targetPayment.setDepositDate(currentTime);
                 
             } else {   
            	 targetPayment.setPaymentDate(currentTime);
             }
    		  paymentService.savePayment(targetPayment);
    	}
    	
        
       // API 응답에 플래너의 이름과 이미지를 포함하여 전달
//        PaymentResponse response = new PaymentResponse(payment, plannerName, plannerImg);
//        return ResponseEntity.ok(response);
        
        return 1;
    }
    
    @PostMapping(value = "/payment/callback")
    public void handleDepositCallback(@RequestBody PaymentCallbackRequest callbackRequest) {
        // 콜백 이벤트 처리 로직
        Long paymentId = callbackRequest.getPaymentId();
        String paymentStatus = callbackRequest.getTempPaymentStatus();
        String depositStatus = callbackRequest.getTempDepositStatus();
        String paymentType = callbackRequest.getPaymentType();

        // 현재 시간 가져옴
        LocalDateTime currentTime = LocalDateTime.now();

        // 데이터베이스에서 해당 paymentId에 해당하는 Payment 객체 가져옴
        Payment payment = paymentService.getPaymentById(paymentId);

        if (paymentType.equals("deposit")) {
            // 계약금 결제 처리
            payment.setDepositStatus(depositStatus);
            payment.setDepositDate(currentTime);
        } else if (paymentType.equals("all")) {
            // 전체 금액 결제 처리
            payment.setPaymentStatus(paymentStatus);
            payment.setPaymentDate(currentTime);
        } else {
            System.out.println("유효하지 않은 결제 유형입니다.");
            return;
        }

        // Payment 객체를 데이터베이스에 저장
        paymentService.savePayment(payment);
    }



}