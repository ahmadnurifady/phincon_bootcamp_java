package com.example.phincon_jdbc.service;

import java.util.List;

import com.example.phincon_jdbc.model.User;

public interface UserService {

    List<User> listAll();

    User get(String userId);

    User save(User user);

    void delete(String userId);
}
