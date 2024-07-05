package com.example.phincon_jdbc.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.phincon_jdbc.model.User;
import com.example.phincon_jdbc.repository.UserRepository;
import com.example.phincon_jdbc.service.UserService;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {
      Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository repo;

    @Override
    public List<User> listAll() {
        return repo.findAll();
    }

    @Override
    public User get(String userId) {
        return repo.findById(userId).get();
    }

    @Override
    public User save(User user) {
        return repo.save(user);
    }

    @Override
    public void delete(String userId) {
        repo.deleteById(userId);
    }
}
