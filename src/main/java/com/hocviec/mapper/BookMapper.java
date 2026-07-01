package com.hocviec.mapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hocviec.dto.BookRequest;
import com.hocviec.dto.BookResponse;
import com.hocviec.entity.Book;
@Component
public class BookMapper {
    public BookMapper(){
}

    public Book toBookEntity(BookRequest book) {

        Book bookEntity = new Book();
        bookEntity.setName(book.getName());
        bookEntity.setPrice(book.getPrice());
        
        return bookEntity;
    }

    public BookResponse toBookResponse(Book book) {

        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setName(book.getName());
        bookResponse.setPrice(book.getPrice());
        bookResponse.setPriceImport(book.getPriceImport());
        bookResponse.setDayCreated(book.getDayCreated());
        bookResponse.setImgUrl(book.getImgUrl());

        return bookResponse;
    }

    public ArrayList<BookResponse> toBookResponseList (ArrayList<Book> books) {

        return books.stream().map(this::toBookResponse).collect(Collectors.toCollection(ArrayList::new));
    }



    public Book updateBook(Book book, BookRequest bookRequest) {
        book.setName(bookRequest.getName());
        book.setPrice(bookRequest.getPrice());	

        return book;
    }

}


