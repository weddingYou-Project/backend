package com.mysite.weddingyou_backend.plannerRegister;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannerRepository extends JpaRepository<Planner, Long> {
    Planner findByEmail(String email);
}
