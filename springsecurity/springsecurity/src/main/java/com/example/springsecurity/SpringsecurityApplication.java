package com.example.springsecurity;

import java.util.List;
import java.util.ArrayList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringsecurityApplication {

    public static void main(String[] args) {

        // SpringApplication.run(SpringsecurityApplication.class, args);
        List<String> name = new ArrayList<String>();
        name.add("garox");
        name.add("yanto");

        List<String> value = new ArrayList<String>();

        for (String el : name) {
            value.add(el);
        }

        System.out.println(value);
    }

}
