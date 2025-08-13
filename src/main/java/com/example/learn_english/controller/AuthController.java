package com.example.learn_english.controller;

import com.example.learn_english.config.jwt.JwtTonkenProvider;
import com.example.learn_english.dto.LoginRequest;
import com.example.learn_english.dto.LoginResponse;
import com.example.learn_english.dto.UserDto;
import com.example.learn_english.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtTonkenProvider tokenProvider;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        String token = tokenProvider.generateToken(auth);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping( value = "/register")
    public ResponseEntity<String> register(@RequestBody UserDto dto) {

        if (dto.getImages() == null || dto.getImages().isEmpty()) {
            dto.setImages(List.of("defalut.jpg"));
        }
        userService.createUser(dto);
        return ResponseEntity.ok("Đăng ký thành công!");
    }

}

