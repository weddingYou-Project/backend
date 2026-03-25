package com.mysite.weddingyou_backend.plannerRegister;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlannerRegisterService {

    @Autowired
    private PlannerRegisterRepository plannerRepository;

    public PlannerRegister getPlannerByEmail(String email) {
        return plannerRepository.findByEmail(email);
    }

    public PlannerRegister createPlanner(PlannerRegisterDTO plannerDTO) {
        PlannerRegister planner = new PlannerRegister();
        planner.setName(plannerDTO.getName());
        planner.setEmail(plannerDTO.getEmail());
        planner.setPassword(plannerDTO.getPassword());
        planner.setPhoneNum(plannerDTO.getPhoneNum());
//        planner.setPlannerImg(plannerDTO.getPlannerImg());
        planner.setGender(plannerDTO.getGender());
        planner.setCareer(plannerDTO.getCareer());
        planner.setPlannerJoinDate(LocalDateTime.now()); // 현재 시간으로 설정
        return plannerRepository.save(planner);
    }
}


