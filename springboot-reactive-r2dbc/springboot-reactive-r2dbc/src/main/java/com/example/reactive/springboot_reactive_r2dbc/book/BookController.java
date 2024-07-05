package com.example.reactive.springboot_reactive_r2dbc.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Book> getAllBooks(@RequestParam(required = false) String title) {
        if (title == null) {
            return bookService.findAll(); 
        }else {
            return bookService.findByTitleContaining(title);
        }
    }

    @GetMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Book>> getBookById(@PathVariable("id") int id) {
        return bookService.findById(id)
                .map(book -> ResponseEntity.ok(book));
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> createBook(@RequestBody Book book) {
        return bookService.save(new Book(book.getTitle(), book.getDescription(), false));
    }

    @PutMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Book> updateBook(@PathVariable("id") int id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteBook(@PathVariable("id") int id) {
        return bookService.deleteById(id);
    }

    @DeleteMapping("/books")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAllBooks() {
        return bookService.deleteAll();
    }

    @GetMapping("/books/published")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Book> findByPublished() {
        return bookService.findByPublished(true);
    }
}
