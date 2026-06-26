package com.hocviec.service;

import com.hocviec.model.Book;
import java.util.ArrayList;

public class BookService {
    
    private ArrayList<Book> books = new ArrayList<>();

    public ArrayList<Book> getALl() {
        return books;
    }

    public Book getById(Long id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
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
