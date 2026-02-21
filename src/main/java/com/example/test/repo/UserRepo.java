package com.example.test.repo;

import com.example.test.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users,Long> {

    boolean existsByEmail(String email);

}
