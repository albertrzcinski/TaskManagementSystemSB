package com.taskmanagementsystem.db;

import com.taskmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByEmail(String email);
    User findByUsername(String username);
    List<User> findAllByUsername (String username);
}
