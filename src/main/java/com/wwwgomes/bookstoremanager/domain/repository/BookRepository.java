package com.wwwgomes.bookstoremanager.domain.repository;

import com.wwwgomes.bookstoremanager.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
