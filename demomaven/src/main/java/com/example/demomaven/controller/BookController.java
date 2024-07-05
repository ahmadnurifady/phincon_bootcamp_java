package com.example.demomaven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.example.demomaven.models.Book;
import com.example.demomaven.repo.BookRepository;
import com.example.exeption.BookIdMismatchException;
import com.example.exeption.BookNotFoundException;



@RestController
@RequestMapping("/api/books")
public class BookController {
    
    @Autowired
    BookRepository bookRepository;

    @GetMapping("/")
    public Iterable<Book> getAllBoks(){
        return bookRepository.findAll();
    }

    @PostMapping("/")
    public Book createBook(@RequestBody Book book){
        return bookRepository.save(book);
    }

    @GetMapping("/title/{bookTitle}")
    public List<Book> getMethodName(@PathVariable String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
        .map(book -> ResponseEntity.ok().body(book))
        .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        bookRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable long id) {
        if (id != book.getId()) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }
    
}
