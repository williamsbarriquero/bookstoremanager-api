package com.wwwgomes.bookstoremanager.domain.repository;

import com.wwwgomes.bookstoremanager.domain.entity.Book;
import com.wwwgomes.bookstoremanager.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByNameAndIsbnAndUser(String name, String isbn, User user);

    Optional<Book> findByIdAndUser(Long id, User user);

    List<Book> findAllByUser(User user);

    void deleteByIdAndUser(Long id, User user);
}
