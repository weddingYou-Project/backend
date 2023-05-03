package com.mysite.weddingyou_backend.planner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, Integer> {
    Planner findByEmail(String email);
}
