package com.mysite.weddingyou_backend.estimate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class EstimateService {

	public final EstimateRepository estimateRepository;
	
	
	public void insert(Estimate data) {
		data.setDate(LocalDate.now());
		estimateRepository.save(data);	
	}
	
	public List<Estimate> getlist(){	//전체 견적 게시글 조회
		List<Estimate> list = estimateRepository.findAll();
		return list;  
	}
	
	
}
