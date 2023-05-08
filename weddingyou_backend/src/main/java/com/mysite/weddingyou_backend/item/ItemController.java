package com.mysite.weddingyou_backend.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {

	@Autowired
	ItemService itemService;
	
	//제품 데이터 로딩 및 처리
	//모든 item 반환->빼도 될 것 같기도..?
	@GetMapping 
    public List<ItemEntity> getAllItems() {
        return itemService.getAllItems();
    }
	
	// 카테고리별 상품 리스트 반환
    @GetMapping("/{category}")
    public List<ItemEntity> getItemsByCategory(@PathVariable String category) {
        return itemService.getItemsByCategory(category);
    }
	
	//검색
	@GetMapping("/search")
    public List<ItemEntity> searchItems(@RequestParam("keyword") String keyword) {
        return itemService.searchItems(keyword);
    }
}