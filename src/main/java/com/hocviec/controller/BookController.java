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
import org.springframework.web.multipart.MultipartFile;

import com.hocviec.dto.BookRequest;
import com.hocviec.dto.BookResponse;
import com.hocviec.dto.response.BaseResponse;
import com.hocviec.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/books")
@Tag(name = "Quản Lý Sách", description = "Các API cung cấp các thao tác Thêm, Sửa, Xóa, Lấy danh sách Sách")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(
        summary = "Lấy toàn bộ danh sách sách",
        description = "API này sẽ truy vấn vào database để lôi ra toàn bộ các cuốn sách đang có trên hệ thống."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tìm kiếm thành công, trả về danh sách sách"),
        @ApiResponse(responseCode = "500", description = "Lỗi hệ thống chạy ngầm hoặc mất kết nối DB")
    })
    public ResponseEntity < BaseResponse < List < BookResponse >>> getAllBooks() {

        BaseResponse < List < BookResponse >> apiResponse = BaseResponse. < List < BookResponse >> builder()
            .result(bookService.getAll())
            .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity < BaseResponse < BookResponse >> getBookById(@PathVariable Long id) {
        BaseResponse < BookResponse > apiResponse = BaseResponse. < BookResponse > builder()
            .result(bookService.getById(id))
            .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    @Operation(
        summary = "Tạo mới một cuốn sách",
        description = "Frontend truyền dữ liệu tên sách và giá vào Body. Hệ thống sẽ validate cú pháp trước khi lưu."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tạo sách thành công"),
        @ApiResponse(responseCode = "400", description = "Dữ liệu đầu vào sai validation cú pháp (Ví dụ: tên trống, giá âm)")
    })
    public ResponseEntity < BaseResponse < Void >> addBook(@Valid @RequestBody BookRequest book) {
        bookService.add(book);
        BaseResponse < Void > apiResponse = BaseResponse. < Void > builder()
            .build();
        return ResponseEntity.status(201).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity < BaseResponse < BookResponse > > updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest book) {

        BaseResponse < BookResponse > apiResponse = BaseResponse. < BookResponse > builder()
            .result(bookService.update(id, book))
            .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity < BaseResponse < Void >> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        BaseResponse < Void > apiResponse = BaseResponse. < Void > builder()
            .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search")
    public ResponseEntity < BaseResponse < List < BookResponse >>> search(@RequestParam(required = false) String name) {
        List < BookResponse > books;
        if (name == null || name.trim().isEmpty()) {
            books = bookService.getAll();
        } else {
            books = bookService.search(name);
        }
        BaseResponse < List < BookResponse >> apiResponse = BaseResponse. < List < BookResponse >> builder()
            .result(books)
            .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search-by-date")
    public ResponseEntity < BaseResponse < List < BookResponse >>> searchByDateBetween(
        @RequestParam LocalDate day1,
        @RequestParam LocalDate day2) {
        List < BookResponse > books = bookService.getByDayCreatedBetween(day1, day2);
        BaseResponse < List < BookResponse >> apiResponse = BaseResponse. < List < BookResponse >> builder()
            .result(books)
            .build();
        return ResponseEntity.ok(apiResponse);
    }

    // API Phân trang: GET http://localhost:8080/api/books/page?page=0&size=5&sortBy=price&direction=desc
    @GetMapping("/page")
    public ResponseEntity < BaseResponse < Page < BookResponse >>> getBooksWithPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {

        BaseResponse < Page < BookResponse >> apiResponse = BaseResponse. < Page < BookResponse >> builder()
            .result(bookService.getAllBooksWithPagination(page, size, sortBy, direction))
            .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/{id}/uploadAvatar")
    public ResponseEntity < BaseResponse < BookResponse >> uploadAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {

        BaseResponse < BookResponse > response = BaseResponse. < BookResponse > builder()
            .code(1000)
            .message("Upload avatar thành công")
            .result(bookService.uploadAvatar(id, file))
            .build();

                return ResponseEntity.ok(response);


            }
    }