package com.mysite.weddingyou_backend.item;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	        items = itemService.getItemsByCategory1AndCategory2(category1, category2);
	         
	        return ResponseEntity.ok().body(items);
	    }
	 
	// 이미지 목록 페이지
		 @RequestMapping("/getItemList/{itemId}")
		    public ResponseEntity<Item> getItemByItemId(
		    		@PathVariable(name = "itemId")String itemId) {
		        Item item =null;
		        item = itemService.getItemById(Long.parseLong(itemId));
		         
		        return ResponseEntity.ok().body(item);
		 }
	 
	 @RequestMapping(value="/itemList/{category1}")
	 public List<String> getImagesByCategory1(@PathVariable Category1 category1) {
		 List<ItemDTO> items =null;
	        items = itemService.getItemsByCategory1(category1);
	       
	        List<String> encodingDatas = new ArrayList<>();
	        
	        
	    if(items!=null) {
	    	for(int i =0;i<items.size();i++) {
	    		ItemDTO targetItem = items.get(i);
	    		Category2 category2 = targetItem.getCategory2();
	    		
		    	 String path = "C:/Project/itemImg/"+targetItem.getCategory1()+"/"+category2;
		    	 Path imagePath = Paths.get(path,targetItem.getItemImg());
		    	 System.out.println(imagePath);

		         try {
		             byte[] imageBytes = Files.readAllBytes(imagePath);
		             byte[] base64encodedData = Base64.getEncoder().encode(imageBytes);
		             
		             encodingDatas.add(new String(base64encodedData));
		             
		         } catch (IOException e) {
		             e.printStackTrace();
		            
		         }
		        encodingDatas.add(String.valueOf(targetItem.getItemId()));
		        System.out.println(targetItem.getItemId());
	    	}
	    	
	    }
	    return encodingDatas;
	    }
	 
	//검색
	@RequestMapping("/search/{keyword}")
	public List<String> searchItems(@PathVariable ("keyword") String keyword) {
		 List<Item> items =new ArrayList<>();
	        items = itemService.searchItems(keyword);
	        System.out.println(items);
	        System.out.println("----------------------------------------------------------------------");
	        
	        List<Item> sortedItems = new ArrayList<>();
	        List<Integer> sortedCount = new ArrayList<>();
	        
	        List<Item> weddingHallItems = new ArrayList<>();
	        List<Item> studioItems = new ArrayList<>();
	        List<Item> dressItems = new ArrayList<>();
	        List<Item> makeupItems = new ArrayList<>();
	        List<Item> honeymoonItems = new ArrayList<>();
	        List<Item> bouquetItems = new ArrayList<>();
	        
	        if(items.size()!=0) {
	        	 for(int i =0;i<items.size();i++) {
	 	        	Category1 category1 = items.get(i).getCategory1();
	 	        	if(category1.toString().equals("웨딩홀")) {
	 	        		weddingHallItems.add(items.get(i));
	 	        	}else if(category1.toString().equals("스튜디오")) {
	 	        		studioItems.add(items.get(i));
	 	        	}else if(category1.toString().equals("의상")) {
	 	        		dressItems.add(items.get(i));
	 	        	}else if(category1.toString().equals("메이크업")) {
	 	        		makeupItems.add(items.get(i));
	 	        	}else if(category1.toString().equals("신혼여행")) {
	 	        		honeymoonItems.add(items.get(i));
	 	        	}else if(category1.toString().equals("부케")) {
	 	        		bouquetItems.add(items.get(i));
	 	        	}
	 	        	
	 	        }
	        	Collections.sort(weddingHallItems, (a, b) -> a.getItemWriteDate().compareTo(b.getItemWriteDate()));
	 	        Collections.sort(studioItems, (a, b) -> a.getItemWriteDate().compareTo(b.getItemWriteDate()));
	 	        Collections.sort(dressItems, (a, b) -> a.getItemWriteDate().compareTo(b.getItemWriteDate()));
	 	        Collections.sort(makeupItems, (a, b) -> a.getItemWriteDate().compareTo(b.getItemWriteDate()));
	 	        Collections.sort(honeymoonItems, (a, b) -> a.getItemWriteDate().compareTo(b.getItemWriteDate()));
	 	        Collections.sort(bouquetItems, (a, b) -> a.getItemWriteDate().compareTo(b.getItemWriteDate()));
	 	        
	 	        Item emptyItem = new Item();
	 	        emptyItem.setItemId(null);
	 	        
	 	        sortedItems.addAll(weddingHallItems);
	 	        sortedItems.add(emptyItem);
	 	        sortedItems.addAll(studioItems);
	 	        sortedItems.add(emptyItem);
	 	        sortedItems.addAll(dressItems); 
	 	        sortedItems.add(emptyItem);
	 	        sortedItems.addAll(makeupItems);
	 	        sortedItems.add(emptyItem);
	 	        sortedItems.addAll(honeymoonItems);
	 	        sortedItems.add(emptyItem);
	 	        sortedItems.addAll(bouquetItems);
	 	        sortedItems.add(emptyItem);
	 	        
	 	        
	        }
	        
	        List<String> encodingDatas = new ArrayList<>();
	        
	        
	    if(items.size()!=0) {
	    	for(int i =0;i<items.size()+6;i++) {
	    		
	    		Item targetItem = sortedItems.get(i);
	    		System.out.println(targetItem.getItemId());
	    		if(targetItem.getItemId()!=null) {
	    			Category2 category2 = targetItem.getCategory2();
		    		
			    	 String path = "C:/Project/itemImg/"+targetItem.getCategory1()+"/"+category2;
			    	 Path imagePath = Paths.get(path,targetItem.getItemImg());
			    	 System.out.println(imagePath);

			         try {
			             byte[] imageBytes = Files.readAllBytes(imagePath);
			             byte[] base64encodedData = Base64.getEncoder().encode(imageBytes);
			             
			             encodingDatas.add(new String(base64encodedData));
			             
			         } catch (IOException e) {
			             e.printStackTrace();
			            
			         }
			         encodingDatas.add(String.valueOf(targetItem.getItemId()));
			         
	    		}
	    		else {
	    			encodingDatas.add("/");
	    			
	    		}
		       
	    	}
	    	
	    }
	    return encodingDatas;
		     
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
		 	itemDTO.setItemName(itemName.toLowerCase());
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
//		    	File folder = new File(path);
//		    	if(!folder.exists()) {
//		    		try {
//		    			folder.mkdir();
//		    		}catch(Exception e) {
//		    			e.getStackTrace();
//		    		}
//		    	}
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
//		    	File folder = new File(path);
//		    	if(!folder.exists()) {
//		    		try {
//		    			folder.mkdir();
//		    		}catch(Exception e) {
//		    			e.getStackTrace();
//		    		}
//		    	}
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

