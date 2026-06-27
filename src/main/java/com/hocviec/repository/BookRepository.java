package com.hocviec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hocviec.model.Book;



@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
}