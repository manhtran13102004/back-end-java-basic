package com.hocviec.book.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hocviec.book.entity.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    List<Book> findByName(String name);

    List<Book> findByNameContaining(String name);

    List<Book> findByDayCreated(LocalDate dayCreated);

    List<Book> findByPriceLessThan(double price);

    List<Book> findByPriceGreaterThan(double price);

    List<Book> findByPriceBetween(double price1, double price2);

    List<Book> findByDayCreatedBetween(LocalDate day1, LocalDate day2);
    
}