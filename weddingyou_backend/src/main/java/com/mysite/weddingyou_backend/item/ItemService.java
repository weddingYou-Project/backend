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
	
	// 카테고리별 상품 리스트 반환
    public List<ItemEntity> getItemsByCategory(String category) {
        return itemRepository.findByCategory(category);
    }
	
	//검색
	public List<ItemEntity> searchItems(String keyword) {
		keyword = keyword.toLowerCase(); // 검색어를 소문자로 변환
        return itemRepository.findByNameContaining(keyword);
    }

}