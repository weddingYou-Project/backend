package com.mysite.weddingyou_backend.item;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.weddingyou_backend.item.Item.Category;

@Controller
public class ItemController {
	
	
	private ItemService itemService;
	
	public ItemController(ItemService itemService) {
	       this.itemService = itemService;
	}
	
	// 이미지 목록 페이지
	 @GetMapping("/itemList")
	    public ResponseEntity<List<ItemDTO>> getItemsSortedBy(
	    		@RequestParam(name = "category")Category category
	    ) {
	        List<ItemDTO> items;
	        items = itemService.getItemsByCategory(category);
	         
	        return ResponseEntity.ok().body(items);
	    }
	 
	 // 이미지 목록 정렬
	 @GetMapping("/sortItems")
	 public ResponseEntity<List<Item>> getItemsByCategorySorted(@RequestParam("category") Category category, 
	                                               @RequestParam(value = "sort", required = false) String sort) {
	     List<Item> items = itemService.getItemsSortedBy(category, sort);

	     return ResponseEntity.ok().body(items);
	 }
    
	 // 새로운 이미지 생성
	 @PostMapping("/insertItem")
	 public ResponseEntity<Item> createItem(@RequestBody ItemDTO itemDTO) {
		    Item newItem = itemService.createItem(itemDTO);
		    return ResponseEntity.ok(newItem);
	}
	 
	 // 이미지 수정(사진 이름 카테고리)
	 @PostMapping("/updateItem")
	 public ResponseEntity<Item> updateItem(@RequestParam(value = "itemId") int itemId, @RequestBody ItemDTO itemDTO) {
	     Item updatedItemDTO = itemService.updateItem(itemId, itemDTO);
	     return ResponseEntity.ok().body(updatedItemDTO);
	 }
    
	 // 이미지 삭제
	 @PostMapping("/deleteItem")
	 public ResponseEntity<String> deleteItem(@RequestParam(value = "itemId") int itemId) {
	     itemService.deleteItem(itemId);
	     return ResponseEntity.ok("Item with id " + itemId + " has been deleted.");
	 }
	 
	 // 좋아요 +1
	 @PostMapping("/like")
     public void increaseLikeCount(@RequestParam(value = "itemId") int itemId) {
         itemService.increaseLikeCount(itemId);
     }

	 // 좋아요 -1
	 @PostMapping("/dislike")
     public void decreaseLikeCount(@RequestParam(value = "itemId") int itemId) {
         itemService.decreaseLikeCount(itemId);
     }

}
