package com.mysite.weddingyou_backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	 @Autowired
	    private UserRepository userRepository;
	    
	    public User getUserByEmail(String email) {
	        return userRepository.findByEmail(email);
	    }
	    
	    public User createUser(User user) {
	        return userRepository.save(user);
	    }
	    
}
