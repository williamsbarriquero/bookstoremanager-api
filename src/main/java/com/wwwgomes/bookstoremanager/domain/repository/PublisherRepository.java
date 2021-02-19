package com.wwwgomes.bookstoremanager.domain.repository;

import com.wwwgomes.bookstoremanager.domain.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
