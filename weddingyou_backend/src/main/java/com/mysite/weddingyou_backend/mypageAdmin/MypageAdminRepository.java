package com.mysite.weddingyou_backend.mypageAdmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MypageAdminRepository extends JpaRepository<MypageAdmin, Long> {

}
