package com.example.learn_english.repository;

import com.example.learn_english.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_USER' ORDER BY u.id DESC")
    List<User> getAllByOrderByIdDesc();

}
