package com.mysite.weddingyou_backend.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long>{
	
	List<ItemEntity> findAll(); //제품 데이터 로딩 및 처리
	
	List<ItemEntity> findByNameContaining(String keyword); //검색 메소드

}
