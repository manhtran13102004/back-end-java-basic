package com.hocviec.controller;

import java.time.LocalDate;
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
import com.hocviec.dto.ApiResponse;

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
    public ResponseEntity < ApiResponse <List < BookResponse >>> getAllBooks() {

        ApiResponse <List <BookResponse>> apiResponse = ApiResponse.<List <BookResponse>>builder()
            .result(bookService.getAll())
            .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity < ApiResponse < BookResponse >> getBookById(@PathVariable Long id) {
        ApiResponse <BookResponse> apiResponse = ApiResponse.<BookResponse>builder()
            .result(bookService.getById(id))
            .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity < ApiResponse < Void >> addBook(@Valid @RequestBody BookRequest book) {
        bookService.add(book);
        ApiResponse <Void> apiResponse = ApiResponse.<Void>builder()
            .build();
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity < ApiResponse < BookResponse > > updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest book) {

        ApiResponse <BookResponse> apiResponse = ApiResponse.<BookResponse>builder()
            .result(bookService.update(id, book))
            .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity < ApiResponse < Void >> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        ApiResponse <Void> apiResponse = ApiResponse.<Void>builder()
            .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookResponse>>> search(@RequestParam(required = false) String name) {
        List<BookResponse> books;
        if (name == null || name.trim().isEmpty()) {
            books = bookService.getAll();
        } else {
            books = bookService.search(name);
        }
        ApiResponse<List<BookResponse>> apiResponse = ApiResponse.<List<BookResponse>>builder()
            .result(books)
            .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search-by-date")
    public ResponseEntity<ApiResponse<List<BookResponse>>> searchByDateBetween(
            @RequestParam LocalDate day1, 
            @RequestParam LocalDate day2) {
        List<BookResponse> books = bookService.getByDayCreatedBetween(day1, day2);
        ApiResponse<List<BookResponse>> apiResponse = ApiResponse.<List<BookResponse>>builder()
            .result(books)
            .build();
        return ResponseEntity.ok(apiResponse);
    }

    // API Phân trang: GET http://localhost:8080/api/books/page?page=0&size=5&sortBy=price&direction=desc
    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> getBooksWithPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {
    
    ApiResponse<Page<BookResponse>> apiResponse = ApiResponse.<Page<BookResponse>>builder()
        .result(bookService.getAllBooksWithPagination(page, size, sortBy, direction))
        .build();
    return ResponseEntity.ok(apiResponse);
    }


}