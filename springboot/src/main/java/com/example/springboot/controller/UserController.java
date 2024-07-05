package com.example.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;


import com.example.springboot.models.User;
import com.example.springboot.repository.UserRepository;

@Controller
@RequestMapping(path = "/demo") 
public class UserController {
    
    @Autowired
    UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email){
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

        @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/findUser")
    public @ResponseBody List<User> findUser(@RequestParam String name, @RequestParam String email){
        return userRepository.findUserUsingQuery(name, email);
    }
    
    @GetMapping(path = "/findUserNative")
    public @ResponseBody List<User> findUserNative(@RequestParam String name, @RequestParam String email){
        return userRepository.findUserUsingNativeQuery(name, email);
    }
    
    @GetMapping(path = "/findUserLike")
    public @ResponseBody List<User> findUserLike(@RequestParam String email){
        return userRepository.findUserUsingNativeQueryLike(email);
    }

    @GetMapping(path = "/findAllUsersSort")
    public @ResponseBody List<User> findAllUsersSort(){
        return userRepository.findAllUsers(Sort.by("name", "email"));
    }
    
}
