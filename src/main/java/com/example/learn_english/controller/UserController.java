package com.example.learn_english.controller;

import com.example.learn_english.dto.UserDto;
import com.example.learn_english.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        boolean deteled = userService.deleteById(id);

        if (deteled){
            return ResponseEntity.ok("Xóa người dùng thành công!");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng!");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUserById(@RequestBody UserDto userDto){
        userService.createUser(userDto);
        return ResponseEntity.ok("Cập nhật thành công!");
    }
}
