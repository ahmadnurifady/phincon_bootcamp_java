package com.example.demomaven.repo;

import org.springframework.data.repository.CrudRepository;

import com.example.demomaven.models.Book;

import java.util.List;


public interface BookRepository extends CrudRepository<Book, Long>{
    List<Book> findByTitle(String title);
    
}
