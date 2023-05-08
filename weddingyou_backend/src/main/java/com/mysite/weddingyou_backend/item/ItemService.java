package com.mysite.weddingyou_backend.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	
	//제품 데이터 로딩 및 처리
	public List<ItemEntity> getAllItems() {
	    return itemRepository.findAll();
	}

}