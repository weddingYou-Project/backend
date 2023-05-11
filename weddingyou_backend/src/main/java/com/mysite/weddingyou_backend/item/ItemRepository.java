package com.mysite.weddingyou_backend.item;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.weddingyou_backend.item.ItemEntity.Category1;
import com.mysite.weddingyou_backend.item.ItemEntity.Category2;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long>{
	
	List<ItemEntity> findAll(); //전체 item 데이터 로딩 및 처리
	
	List<ItemEntity> findByCategory(String category); //카테고리별 item을 불러오는 메소드
	
	List<ItemEntity> findByNameContaining(String keyword); //검색 메소드
	
	Optional<ItemEntity> findById(Long itemId);
	
	List<ItemEntity> findByCategory1AndCategory2(Category1 category1, Category2 category2);

}
