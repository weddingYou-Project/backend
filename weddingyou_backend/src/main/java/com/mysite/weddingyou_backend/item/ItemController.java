package com.mysite.weddingyou_backend.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

	@Autowired
	ItemService itemService;
	
	//제품 데이터 로딩 및 처리
	@GetMapping("/items")
    public List<ItemEntity> getAllItems() {
        return itemService.getAllItems();
    }
}