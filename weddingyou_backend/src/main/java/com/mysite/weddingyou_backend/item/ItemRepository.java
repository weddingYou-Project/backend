package com.mysite.weddingyou_backend.item;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.weddingyou_backend.item.Item.Category1;
import com.mysite.weddingyou_backend.item.Item.Category2;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
	
	List<Item> findAll(); //전체 item 데이터 로딩 및 처리
	
	//List<Item> findByCategory1(Category1 category1); //카테고리별 item을 불러오는 메소드
	
	List<Item> findByItemNameContaining(String keyword); //검색 메소드
	
	Optional<Item> findById(Long itemId);
	
	List<Item> findByCategory1AndCategory2(Category1 category1, Category2 category2);

}
