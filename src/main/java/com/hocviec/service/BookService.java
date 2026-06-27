package com.hocviec.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hocviec.dto.BookRequest;
import com.hocviec.dto.BookResponse;
import com.hocviec.model.Book;

@Service
public class BookService {
    
    private final ArrayList<Book> books = new ArrayList<>();

    public ArrayList<BookResponse> getAll() {
        return books.stream()
                .map(book -> new BookResponse(book.getId(), book.getName(), book.getPrice(), book.getPriceImport(), book.getDayCreated()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Optional<BookResponse> getById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .map(book -> new BookResponse(book.getId(), book.getName(), book.getPrice(), book.getPriceImport(), book.getDayCreated()))
                .findFirst();
    }

    public void add(BookRequest request) {
        Book book = new Book();
        book.setId((long) (books.size() + 1));
        book.setName(request.getName());
        book.setPrice(request.getPrice());
        book.setDayCreated(java.time.LocalDate.now());
        book.setPriceImport(request.getPrice() * 0.5);

        books.add(book);
    }

    public void update(Long id, BookRequest bookRequest) {
        books.stream()
		.filter(book -> book.getId().equals(id))
		.findFirst()
		.ifPresent(book -> {
			book.setName(bookRequest.getName());
			book.setPrice(bookRequest.getPrice());
		});
    }

    public void delete(Long id) {
        Optional<Book> bookOptional = books.stream()
        .filter(book -> book.getId().equals(id))
        .findFirst();
	    bookOptional.ifPresent(book -> books.remove(book));

    }
}
