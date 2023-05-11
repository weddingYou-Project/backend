package com.mysite.weddingyou_backend.item;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.weddingyou_backend.item.Item.Category1;
import com.mysite.weddingyou_backend.item.Item.Category2;
import com.mysite.weddingyou_backend.like.LikeEntity;

@RestController
@RequestMapping("/items")
public class ItemController {

	private ItemService itemService;
	
	public ItemController(ItemService itemService) {
	       this.itemService = itemService;
	}
	
	//제품 데이터 로딩 및 처리
	//모든 item 반환->빼도 될 것 같기도..?
	@GetMapping 
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }
	
	// 카테고리별 상품 리스트 반환
    @GetMapping("/{category}")
    public List<Item> getItemsByCategory(@PathVariable String category) {
        return itemService.getItemsByCategory(category);
    }
	
	//검색
	@GetMapping("/search")
    public List<Item> searchItems(@RequestParam("keyword") String keyword) {
        return itemService.searchItems(keyword);
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
			 		String path1 = "C:\\Project\\itemImg\\";
			    	String path2 = "C:\\Project\\itemImg\\"+category1;
			     	String path3 = "C:\\Project\\itemImg\\"+category1+"\\"+category2;
			     	File folder1 = new File(path1);
			    	File folder2 = new File(path2);
			    	File folder3 = new File(path3);
			    	if(!folder1.exists() || !folder2.exists() || !folder3.exists()) {
			    		try {
			    			folder1.mkdir();
			    			folder2.mkdir();
			    			folder3.mkdir();
			    		}catch(Exception e) {
			    			e.getStackTrace();
			    		}
			    	}
			    	
			    
			        Files.copy(file.getInputStream(), Paths.get(path3, file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING); //request에서 들어온 파일을 uploads 라는 경로에 originalfilename을 String 으로 올림
			       
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
		 public ResponseEntity<Item> updateItem(@RequestParam("file") MultipartFile file,@RequestParam(value = "itemId") Long itemId,  @RequestBody ItemDTO itemDTO) {
			 
			 	try {	
			 		
			    	String path = "C:\\Project\\itemImg\\"+itemDTO.getCategory1()+"\\"+itemDTO.getCategory2();
//			    	File folder = new File(path);
//			    	if(!folder.exists()) {
//			    		try {
//			    			folder.mkdir();
//			    		}catch(Exception e) {
//			    			e.getStackTrace();
//			    		}
//			    	}
			    	Item deleteItem = itemService.getItemById(itemId);
			    	Path deleteFilePath = Paths.get(path, deleteItem.getItemImg());
			    	Files.delete(deleteFilePath);
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
		 public ResponseEntity<String> deleteItem(@RequestParam(value = "itemId") Long itemId, @RequestBody ItemDTO itemDTO) {
			 try {	
			 		
			    	String path = "C:\\Project\\itemImg\\"+itemDTO.getCategory1()+"\\"+itemDTO.getCategory2();
//			    	File folder = new File(path);
//			    	if(!folder.exists()) {
//			    		try {
//			    			folder.mkdir();
//			    		}catch(Exception e) {
//			    			e.getStackTrace();
//			    		}
//			    	}
			    	Item deleteItem = itemService.getItemById(itemId);
			    	Path deleteFilePath = Paths.get(path, deleteItem.getItemImg());
			    	Files.delete(deleteFilePath);
			       
			        itemService.deleteItem(itemId); // 이미지파일이름 데이터베이스에 업데이트함
			        return ResponseEntity.ok().body("Item with id " + itemId + " has been deleted.");
			    } catch (IOException e) {
			        e.printStackTrace();
			        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			    }
		   
		 }
		 
		 @RequestMapping("/getLikeCount")
		 public int getLikeCount(@RequestParam(value = "itemId") Long itemId) {
			
			int likeCount =  itemService.getLikeCount(itemId);
			return likeCount;
		 }
}