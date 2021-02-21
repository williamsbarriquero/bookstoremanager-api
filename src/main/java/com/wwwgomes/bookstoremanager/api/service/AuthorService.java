package com.wwwgomes.bookstoremanager.api.service;

import com.wwwgomes.bookstoremanager.api.mapper.AuthorMapper;
import com.wwwgomes.bookstoremanager.domain.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final static AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
}
