package com.example.phincon_jdbc.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.phincon_jdbc.model.User;


public interface  UserRepository extends JpaRepository<User, String>{
    
}
