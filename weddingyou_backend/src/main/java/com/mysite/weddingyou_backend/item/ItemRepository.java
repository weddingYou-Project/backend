package com.mysite.weddingyou_backend.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.weddingyou_backend.item.Item.Category;

public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByCategory(Category category);
}
