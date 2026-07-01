package com.hocviec.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hocviec.dto.BookRequest;
import com.hocviec.dto.BookResponse;
import com.hocviec.entity.Book;
import com.hocviec.exception.AppException;
import com.hocviec.exception.ErrorCode;
import com.hocviec.mapper.BookMapper;
import com.hocviec.repository.BookRepository;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final FileService fileService;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, FileService fileService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.fileService = fileService;
    }

    public List < BookResponse > getAll() {

        List < Book > books = bookRepository.findAll();
        return books.stream()
            .map(bookMapper::toBookResponse)
            .collect(Collectors.toList());
    }

    public Page < BookResponse > getAllBooksWithPagination(int page, int size, String sortBy, String direction) {

        // 1. Xác định chiều sắp xếp (Tăng dần hay Giảm dần)
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
            Sort.by(sortBy).ascending() :
            Sort.by(sortBy).descending();

        // 2. Tạo đối tượng Pageable (Lưu ý: Trong Spring, trang đầu tiên bắt đầu từ số 0)
        Pageable pageable = PageRequest.of(page, size, sort);

        // 3. Gọi Repo để kéo đúng số lượng dữ liệu dưới DB lên
        Page < Book > bookPage = bookRepository.findAll(pageable);

        // 4. Ánh xạ Page<Book> sang Page<BookResponse> bằng Mapper
        return bookPage.map(bookMapper::toBookResponse);
    }

    public BookResponse getById(Long id) {
        return bookRepository.findById(id)
            .map(bookMapper::toBookResponse)
            .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));
    }

    public BookResponse add(BookRequest request) {
        Book book = bookMapper.toBookEntity(request);
        book.setDayCreated(java.time.LocalDate.now());
        book.setPriceImport(request.getPrice() * 0.5);

        Book savedBook = bookRepository.save(book);
        return bookMapper.toBookResponse(savedBook);
    }

    public BookResponse update(Long id, BookRequest bookRequest) {
        Book existingBook = bookRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));

        Book updatedBook = bookMapper.updateBook(existingBook, bookRequest);

        Book finalBook = bookRepository.save(updatedBook);
        return bookMapper.toBookResponse(finalBook);
    }

    public void delete(Long id) {

        if (!bookRepository.existsById(id)) {
            throw new AppException(ErrorCode.BOOK_NOT_EXISTED);
        }
        bookRepository.deleteById(id);
    }

    public List < BookResponse > search(String name) {
        return bookRepository.findByNameContaining(name).stream()
            .map(book -> bookMapper.toBookResponse(book))
            .collect(Collectors.toList());
    }


    public List < BookResponse > getByDayCreated(LocalDate dayCreated) {
        return bookRepository.findByDayCreated(dayCreated).stream()
            .map(book -> bookMapper.toBookResponse(book))
            .collect(Collectors.toList());
    }

    public List < BookResponse > getByPriceLessThan(double price) {
        return bookRepository.findByPriceLessThan(price).stream()
            .map(book -> bookMapper.toBookResponse(book))
            .collect(Collectors.toList());
    }

    public List < BookResponse > getByPriceGreaterThan(double price) {
        return bookRepository.findByPriceGreaterThan(price).stream()
            .map(book -> bookMapper.toBookResponse(book))
            .collect(Collectors.toList());
    }

    public List < BookResponse > getByPriceBetween(double price1, double price2) {
        return bookRepository.findByPriceBetween(price1, price2).stream()
            .map(book -> bookMapper.toBookResponse(book))
            .collect(Collectors.toList());
    }

    public List < BookResponse > getByDayCreatedBetween(LocalDate day1, LocalDate day2) {
        return bookRepository.findByDayCreatedBetween(day1, day2).stream()
            .map(book -> bookMapper.toBookResponse(book))
            .collect(Collectors.toList());
    }

    public BookResponse uploadAvatar(Long id, MultipartFile file) {


        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));

                String fileName = fileService.uploadFile(file);

                book.setImgUrl(fileName);

                bookRepository.save(book);

                return bookMapper.toBookResponse(book);
            }

    }