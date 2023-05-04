package com.mysite.weddingyou_backend.userRegister;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRegisterService {

    @Autowired
    private UserRegisterRepository userRepository;

    public UserRegister getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserRegister createUser(UserRegisterDTO userDTO) {
        UserRegister user = new UserRegister();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNum(userDTO.getPhoneNum());
        user.setGender(userDTO.getGender());
//        user.setUserImg(userDTO.getUserImg());
        user.setUserJoinDate(LocalDateTime.now()); // 현재 시간으로 설정
        return userRepository.save(user);
    }
}

