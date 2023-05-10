package com.mysite.weddingyou_backend.item;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.weddingyou_backend.item.Item.Category1;
import com.mysite.weddingyou_backend.item.Item.Category2;
import com.mysite.weddingyou_backend.plannerUpdateDelete.PlannerUpdateDelete;

@Service
public class ItemService {
	
	private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
   

    public List<Item> getItemsSortedBy(Category1 category1, Category2 category2, String sort) {
        List<Item> itemList = itemRepository.findByCategory1AndCategory2(category1, category2);
    
        if (sort == null || sort.equals("latest")) {
            Collections.sort(itemList, (a, b) -> b.getItemWriteDate().compareTo(a.getItemWriteDate()));
        } else if (sort.equals("likeCount")) {
        	//likeid로 불러온 like 테이블 내의 likecount를 get하는 함수 쓰기
         //   Collections.sort(itemList, (a, b) -> b.getLikeCount() - a.getLikeCount());
        } else if (sort.equals("name")) {
            Collections.sort(itemList, (a, b) -> a.getItemName().compareTo(b.getItemName()));
        }

        return itemList;
    }
    
    public List<ItemDTO> getItemsByCategory(Category1 category1, Category2 category2) {
        List<Item> items = itemRepository.findByCategory1AndCategory2(category1, category2);
        List<ItemDTO> itemDTOs = new ArrayList<>();
        for (Item item : items) {
            itemDTOs.add(ItemDTO.fromEntity(item));
        }
        return itemDTOs;
    }

    public Item getItemById(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            return optionalItem.get();
        } else {
            throw new RuntimeException("Item not found with id: " + itemId);
        }
    }
    
    public Item createItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setImgContent(itemDTO.getContent());
        item.setItemName(itemDTO.getItemName());
        item.setCategory1(itemDTO.getCategory1());
        item.setCategory2(itemDTO.getCategory2());
        item.setItemImg(itemDTO.getItemImg());
     //   item.setLikeCount(0);
        item.setItemWriteDate(LocalDateTime.now());
        return itemRepository.save(item);
    }
    

	 

    public Item updateItem(Long itemId, ItemDTO itemDTO) {
        Item item = getItemById(itemId);
        item.setItemName(itemDTO.getItemName());
        item.setCategory1(itemDTO.getCategory1());
        item.setCategory2(itemDTO.getCategory2());
        item.setImgContent(itemDTO.getContent());
        return itemRepository.save(item);
    }

    public void deleteItem(Long itemId) {
        Item item = getItemById(itemId);
        itemRepository.delete(item);
    }

//    public void increaseLikeCount(Long itemId) {
//        Item item = getItemById(itemId);
//        item.setLikeCount(item.getLikeCount() + 1);
//        itemRepository.save(item);
//    }
//
//    public void decreaseLikeCount(Long itemId) {
//        Item item = getItemById(itemId);
//        if(item.getLikeCount()!=0) { //item likecount 음수 불가
//        	item.setLikeCount(item.getLikeCount() - 1);
//        }
//        
//        itemRepository.save(item);
//    }
	
}
