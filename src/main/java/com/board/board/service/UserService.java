package com.board.board.service;

import com.board.board.model.User;
import com.board.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public  User createUser(User user){
        return userRepository.save(user);
    }

    public User getUser(Long id){
        return  userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found"));
    }
}
