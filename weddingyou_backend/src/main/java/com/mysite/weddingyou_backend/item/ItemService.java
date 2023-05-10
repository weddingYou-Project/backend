package com.mysite.weddingyou_backend.item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.weddingyou_backend.item.Item.Category1;
import com.mysite.weddingyou_backend.item.Item.Category2;
import com.mysite.weddingyou_backend.like.LikeEntity;

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
        item.setItemWriteDate(LocalDateTime.now());
        return itemRepository.save(item);
    }

    public void deleteItem(Long itemId) {
        Item item = getItemById(itemId);
        itemRepository.delete(item);
    }

    public int getLikeCount(Long itemId) {
    	int like_count=0;
    	List<LikeEntity> likeEntities = new ArrayList<>();
    	Item item = getItemById(itemId);
    	likeEntities = item.getLike();
    	like_count = likeEntities.size();
		return like_count;
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

