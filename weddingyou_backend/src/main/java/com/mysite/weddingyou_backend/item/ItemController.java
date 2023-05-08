package com.mysite.weddingyou_backend.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

	@Autowired
	ItemService itemService;
	
	//제품 데이터 로딩 및 처리
	@PostMapping("/items")
    public List<ItemEntity> getAllItems() {
        return itemService.getAllItems();
    }
	
	//검색
	@PostMapping("/search")
    public List<ItemEntity> searchItems(@RequestParam("keyword") String keyword) {
        return itemService.searchItems(keyword);
    }
}