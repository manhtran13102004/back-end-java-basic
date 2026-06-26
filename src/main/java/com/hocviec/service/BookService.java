package com.hocviec.service;

import com.hocviec.model.Book;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class BookService {
    
    private ArrayList<Book> books = new ArrayList<>();

    public ArrayList<Book> getAll() {
        return books;
    }

    public Optional<Book> getById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public void add(Book book) {
        books.add(book);
    }

    public void update(Book book) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(book.getId())) {
                books.set(i, book);
                return;
            }
        }
    }

    public void delete(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }
}
