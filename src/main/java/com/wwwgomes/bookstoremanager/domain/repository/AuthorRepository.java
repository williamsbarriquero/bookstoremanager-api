package com.wwwgomes.bookstoremanager.domain.repository;

import com.wwwgomes.bookstoremanager.domain.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
