package com.mysite.weddingyou_backend.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PaymentService {
	
	@Autowired
	PaymentRepository paymentRepository;

}
