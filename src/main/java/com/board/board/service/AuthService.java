package com.board.board.service;

import com.board.board.config.JwtUtill;
import com.board.board.dto.LoginRequest;
import com.board.board.dto.SignupRequest;
import com.board.board.model.User;
import com.board.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtill jwtUtill;

    public String login(LoginRequest request){

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(),  user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return jwtUtill.generateToken(user.getUsername());
    }

    public void signup(SignupRequest request){

        if(userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");

        }

        User user = new User();
        user.setUsername(request.getUsername());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }
}
