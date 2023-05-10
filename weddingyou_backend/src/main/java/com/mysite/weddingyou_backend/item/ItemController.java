package com.mysite.weddingyou_backend.item;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.weddingyou_backend.item.Item.Category1;
import com.mysite.weddingyou_backend.item.Item.Category2;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	
	private ItemService itemService;
	
	public ItemController(ItemService itemService) {
	       this.itemService = itemService;
	}
	

	// 이미지 목록 페이지
	 @GetMapping("/itemList")
	    public ResponseEntity<List<ItemDTO>> getItemsSortedBy(
	    		@RequestParam(name = "category1")Category1 category1, @RequestParam(name = "category2")Category2 category2
	    ) {
	        List<ItemDTO> items =null;
	        items = itemService.getItemsByCategory(category1, category2);
	         
	        return ResponseEntity.ok().body(items);
	    }
	 
	 // 이미지 목록 정렬
	 @GetMapping("/sortItems")
	 public ResponseEntity<List<Item>> getItemsByCategorySorted(@RequestParam("category1") Category1 category1, 
			 @RequestParam("category2") Category2 category2, @RequestParam(value = "sort", required = false) String sort) {
	     List<Item> items = itemService.getItemsSortedBy(category1,category2, sort);

	     return ResponseEntity.ok().body(items);
	 }
    
	 // 새로운 이미지 생성
	 @RequestMapping("/insertItem")
	 public ResponseEntity<Item> createItem(@RequestParam("file") MultipartFile file,@RequestParam("category1") Category1 category1, 
			 @RequestParam("category2") Category2 category2,@RequestParam("itemName") String itemName,
			 @RequestParam("content")String content ) {
		 	ItemDTO itemDTO = new ItemDTO();
		 	itemDTO.setCategory1(category1);
		 	itemDTO.setCategory2(category2);
		 	itemDTO.setContent(content);
		 	itemDTO.setItemName(itemName);
		 	//itemDTO.setItemWriteDate(LocalDateTime.now());
		 	//itemDTO.setLikeCount(0);
		 	try {	
				  
		    	String path = "C:\\Project\\ItemImg\\"+category1+"\\"+category2;
		    	File folder = new File(path);
		    	if(!folder.exists()) {
		    		try {
		    			folder.mkdir();
		    		}catch(Exception e) {
		    			e.getStackTrace();
		    		}
		    	}
		    	
		        Files.copy(file.getInputStream(), Paths.get(path, file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING); //request에서 들어온 파일을 uploads 라는 경로에 originalfilename을 String 으로 올림
		        System.out.println(file.getInputStream());
		        itemDTO.setItemImg(file.getOriginalFilename()); //itemimg에다가 이미지 파일 이름 저장
		        Item newItem = itemService.createItem(itemDTO); // 이미지파일이름 데이터베이스에 업데이트함
		        return ResponseEntity.ok(newItem);
		    } catch (IOException e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		    }
		  
	}
	 
	
	 
	 // 이미지 수정(사진 이름 카테고리)
	 @RequestMapping("/updateItem")
	 public ResponseEntity<Item> updateItem(@RequestParam("file") MultipartFile file,@RequestParam(value = "itemId") Long itemId, @RequestBody ItemDTO itemDTO) {
		 
		 	try {	
				  
		    	String path = "C:\\Project\\ItemImg\\"+itemDTO.getCategory1()+"\\"+itemDTO.getCategory2();
		    	File folder = new File(path);
		    	if(!folder.exists()) {
		    		try {
		    			folder.mkdir();
		    		}catch(Exception e) {
		    			e.getStackTrace();
		    		}
		    	}
		    	
		        Files.copy(file.getInputStream(), Paths.get(path, file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING); //request에서 들어온 파일을 uploads 라는 경로에 originalfilename을 String 으로 올림
		        System.out.println(file.getInputStream());
		        itemDTO.setItemImg(file.getOriginalFilename()); //itemimg에다가 이미지 파일 이름 저장
		        Item updatedItemDTO = itemService.updateItem(itemId,itemDTO); // 이미지파일이름 데이터베이스에 업데이트함
		        return ResponseEntity.ok().body(updatedItemDTO);
		    } catch (IOException e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		    }
	
	 }
    
	 // 이미지 삭제
	 @PostMapping("/deleteItem")
	 public ResponseEntity<String> deleteItem(@RequestParam(value = "itemId") Long itemId) {
	     itemService.deleteItem(itemId);
	     return ResponseEntity.ok("Item with id " + itemId + " has been deleted.");
	 }
	 
	 @RequestMapping("/getLikeCount")
	 public int getLikeCount(@RequestParam(value = "itemId") Long itemId) {
		int likeCount =  itemService.getLikeCount(itemId);
		return likeCount;
	 }
	 
	
	 
//	 // 좋아요 +1
//	 @PostMapping("/like")
//     public void increaseLikeCount(@RequestParam(value = "itemId") Long itemId) {
//         itemService.increaseLikeCount(itemId);
//     }
//
//	 // 좋아요 -1
//	 @PostMapping("/dislike")
//     public void decreaseLikeCount(@RequestParam(value = "itemId") Long itemId) {
//         itemService.decreaseLikeCount(itemId);
//     }

}

