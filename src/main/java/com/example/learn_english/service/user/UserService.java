package com.example.learn_english.service.user;

import com.example.learn_english.dto.UserDto;
import com.example.learn_english.model.Role;
import com.example.learn_english.model.User;
import com.example.learn_english.repository.IRoleRepository;
import com.example.learn_english.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepo;
    private final IRoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public void createUser(UserDto dto) {
        User user;
        if (dto.getId() != null) {
            user = userRepo.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        } else {
            user = new User();
        }
        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(encoder.encode(dto.getPassword()));
        }

        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(r -> roleRepo.findByName(r).orElseThrow())
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        user.setAge(dto.getAge());
        user.setName(dto.getName());

        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            user.setImageUrl(dto.getImages().get(0));
        }


        userRepo.save(user);
    }

    public List<UserDto> getAllUser(){
        return userRepo.getAllByOrderByIdDesc().stream()
                .map(user -> {
                    UserDto dto = new UserDto();
                    dto.setId(user.getId());
                    dto.setEmail(user.getEmail());
                    dto.setPassword(user.getPassword());
                    dto.setRoles(user.getRoles().stream()
                            .map(Role::getName)
                            .collect(Collectors.toSet()));
                    dto.setAge(user.getAge());
                    dto.setName(user.getName());
                    dto.setImages(Collections.singletonList(user.getImageUrl()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public boolean deleteById(Long id) {
        if (id != null) {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public void updateUserById(Long id, UserDto dto) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        existingUser.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            existingUser.setPassword(encoder.encode(dto.getPassword()));
        }

        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<Role> roles = dto.getRoles().stream()
                    .map(r -> roleRepo.findByName(r).orElseThrow())
                    .collect(Collectors.toSet());
            existingUser.setRoles(roles);
        }

        existingUser.setAge(dto.getAge());
        existingUser.setName(dto.getName());

        if (dto.getImages() != null && !dto.getImages().isEmpty()) {
            existingUser.setImageUrl(dto.getImages().get(0));
        }

        userRepo.save(existingUser);
    }



}
