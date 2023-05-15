package com.mysite.weddingyou_backend.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;

}
