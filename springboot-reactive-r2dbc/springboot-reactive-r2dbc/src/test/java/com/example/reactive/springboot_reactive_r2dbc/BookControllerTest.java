package com.example.reactive.springboot_reactive_r2dbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.reactive.springboot_reactive_r2dbc.book.Book;
import com.example.reactive.springboot_reactive_r2dbc.book.BookController;
import com.example.reactive.springboot_reactive_r2dbc.book.BookRepository;
import com.example.reactive.springboot_reactive_r2dbc.book.BookService;
import com.example.reactive.springboot_reactive_r2dbc.exeption.BookNotFoundException;
import com.example.reactive.springboot_reactive_r2dbc.exeption.errorResponse.ErrorResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = BookController.class)
@AutoConfigureWebTestClient
@Slf4j
public class BookControllerTest {

    private final WebTestClient webTestClient;

    @MockBean
    private final BookRepository repository;

    @MockBean
    private final BookService bookService;

    @MockBean
    ConnectionFactoryInitializer initializer;

    private final String url = "/api/books";

    @Autowired
    public BookControllerTest(WebTestClient webTestClient, BookService bookService, BookRepository repo) {
        this.webTestClient = webTestClient;
        this.bookService = bookService;
        this.repository = repo;
    }

    @Test
    void findBookByIdOk() {
        var book = Book.builder().id(1).title("test title 1").description("test description 1").build();
        log.info("book: {}",book);

        when(bookService.findById(book.getId())).thenReturn(Mono.just(book));

        String findBookByIdUrl = String.format("%s/%s", url, book.getId());
        log.info("url: {}",findBookByIdUrl);
        webTestClient
                .get()
                .uri(findBookByIdUrl)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Book.class)
                .consumeWith(result -> {
                    var existBook = result.getResponseBody();

                    assert existBook != null;
                    assertEquals(book.getId(), existBook.getId());
                    assertEquals(book.getTitle(), existBook.getTitle());

                });
    }

    @Test
    void findBookByIdErrorNotFound() {
        var book = Book.builder().id(3).title("test title 3").description("test description 3").build();

        when(bookService.findById(book.getId())).thenReturn(Mono.error(new BookNotFoundException(String.format("Book not found. ID: %s", book.getId()))));

        String findTodoByIdUrl = String.format("%s/%s", url, book.getId());
        webTestClient
                .get()
                .uri(findTodoByIdUrl)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(ErrorResponse.class)
                .consumeWith(result -> {
                    var errorResponse = result.getResponseBody();

                    assertTrue(errorResponse.getStatusCode() !=  HttpStatus.OK.value());
                    assertEquals("Book not found. ID: 3", errorResponse.getMessage());
                });
    }
//    @Test
//    void createBookTest(){
//        var book = Book.builder().id(2).title("test title 2").description("test description 2").build();
//
//        when(bookService.save(any(Book.class))).thenReturn(Mono.just(book));
//
//        String createBookUrl = String.format("%s", url);
//        webTestClient
//                .post()
//                .uri(createBookUrl)
//                .exchange()
//                .expectStatus()
//                .isCreated()
//                .expectBody(Book.class)
//                .consumeWith(result -> {
//                   var existBook = result.getResponseBody();
//
//                   assertEquals(existBook.getId(), book.getId());
//                    assertEquals(existBook.getTitle(), book.getTitle());
//                    assertEquals(existBook.getDescription(), book.getDescription());
//                });
//    }
}
