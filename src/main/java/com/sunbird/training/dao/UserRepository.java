package com.sunbird.training.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbird.training.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{
    User findByUsername(String username);
}