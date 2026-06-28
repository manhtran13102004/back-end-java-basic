package com.hocviec.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hocviec.dto.BookRequest;
import com.hocviec.dto.BookResponse;
import com.hocviec.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity < ArrayList < BookResponse >> getAllBooks() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity < BookResponse > getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PostMapping
    public ResponseEntity < Void > addBook(@Valid @RequestBody BookRequest book) {
        bookService.add(book);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity < BookResponse > updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest book) {

        return ResponseEntity.ok(bookService.update(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity < Void > deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> search(@RequestParam(required = false) String name) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.ok(bookService.getAll());
        }
        return ResponseEntity.ok(bookService.search(name));
    }

    @GetMapping("/search-by-date")
    public ResponseEntity<List<BookResponse>> searchByDateBetween(
            @RequestParam LocalDate day1, 
            @RequestParam LocalDate day2) {
        return ResponseEntity.ok(bookService.getByDayCreatedBetween(day1, day2));
    }

    // API Phân trang: GET http://localhost:8080/api/books/page?page=0&size=5&sortBy=price&direction=desc
    @GetMapping("/page")
    public ResponseEntity<Page<BookResponse>> getBooksWithPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {
    
    return ResponseEntity.ok(bookService.getAllBooksWithPagination(page, size, sortBy, direction));
    }


}