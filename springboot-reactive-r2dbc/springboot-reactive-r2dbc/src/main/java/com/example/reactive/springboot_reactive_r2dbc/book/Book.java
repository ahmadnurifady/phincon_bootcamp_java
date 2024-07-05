package com.example.reactive.springboot_reactive_r2dbc.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    private int id;

    private String title;

    private String description;

    private boolean published;

    public Book(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }
}
