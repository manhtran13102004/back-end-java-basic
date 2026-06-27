package com.hocviec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hocviec.dto.BookRequest;
import com.hocviec.dto.BookResponse;
import com.hocviec.mapper.BookMapper;
import com.hocviec.model.Book;
import com.hocviec.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public ArrayList<BookResponse> getAll() {

        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> new BookResponse(book.getId(), book.getName(), book.getPrice(), book.getPriceImport(), book.getDayCreated()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Optional<BookResponse> getById(Long id) {
        return bookRepository.findById(id).map(bookMapper::toBookResponse);
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
                .orElseThrow(() -> new RuntimeException("KHÔNG TÌM THẤY ID: " + id));

        Book updatedBook = bookMapper.updateBook(existingBook, bookRequest);

        Book finalBook = bookRepository.save(updatedBook);
        return bookMapper.toBookResponse(finalBook);
    }

    public void delete(Long id) {
       
       if (bookRepository.existsById(id)) {
        bookRepository.deleteById(id);
       }

    }
}
