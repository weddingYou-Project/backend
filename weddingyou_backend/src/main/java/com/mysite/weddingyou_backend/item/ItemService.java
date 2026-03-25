package com.mysite.weddingyou_backend.item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.weddingyou_backend.item.Item.Category;

@Service
public class ItemService {
	
	private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getItemsSortedBy(Category category, String sort) {
        List<Item> itemList = itemRepository.findByCategory(category);

        if (sort == null || sort.equals("latest")) {
            Collections.sort(itemList, (a, b) -> b.getItemWriteDate().compareTo(a.getItemWriteDate()));
        } else if (sort.equals("likeCount")) {
            Collections.sort(itemList, (a, b) -> b.getLikeCount() - a.getLikeCount());
        } else if (sort.equals("name")) {
            Collections.sort(itemList, (a, b) -> a.getItemName().compareTo(b.getItemName()));
        }

        return itemList;
    }
    
    public List<ItemDTO> getItemsByCategory(Category category) {
        List<Item> items = itemRepository.findByCategory(category);
        List<ItemDTO> itemDTOs = new ArrayList<>();
        for (Item item : items) {
            itemDTOs.add(ItemDTO.fromEntity(item));
        }
        return itemDTOs;
    }

    public Item getItemById(int itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            return optionalItem.get();
        } else {
            throw new RuntimeException("Item not found with id: " + itemId);
        }
    }
    
    public Item createItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setItemImg(itemDTO.getItemImg());
        item.setItemName(itemDTO.getItemName());
        item.setCategory(itemDTO.getCategory());
        item.setLikeCount(0);
        item.setItemWriteDate(LocalDateTime.now());
        return itemRepository.save(item);
    }

    public Item updateItem(int itemId, ItemDTO itemDTO) {
        Item item = getItemById(itemId);
        item.setItemName(itemDTO.getItemName());
        item.setItemImg(itemDTO.getItemImg());
        item.setCategory(itemDTO.getCategory());
        return itemRepository.save(item);
    }

    public void deleteItem(int itemId) {
        Item item = getItemById(itemId);
        itemRepository.delete(item);
    }

    public void increaseLikeCount(int itemId) {
        Item item = getItemById(itemId);
        item.setLikeCount(item.getLikeCount() + 1);
        itemRepository.save(item);
    }

    public void decreaseLikeCount(int itemId) {
        Item item = getItemById(itemId);
        item.setLikeCount(item.getLikeCount() - 1);
        itemRepository.save(item);
    }
	
}
