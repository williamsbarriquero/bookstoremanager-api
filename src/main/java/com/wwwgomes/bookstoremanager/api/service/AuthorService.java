package com.wwwgomes.bookstoremanager.api.service;

import com.wwwgomes.bookstoremanager.api.dto.AuthorDTO;
import com.wwwgomes.bookstoremanager.api.mapper.AuthorMapper;
import com.wwwgomes.bookstoremanager.domain.entity.Author;
import com.wwwgomes.bookstoremanager.domain.repository.AuthorRepository;
import com.wwwgomes.bookstoremanager.exception.AuthorAlreadyExistsException;
import com.wwwgomes.bookstoremanager.exception.AuthorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private static final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDTO create(AuthorDTO authorDTO) {
        verifyIfExists(authorDTO.getName());

        var authorToCreate = authorMapper.toModel(authorDTO);
        var createdAuthor = authorRepository.save(authorToCreate);

        return authorMapper.toDTO(createdAuthor);
    }

    public AuthorDTO findById(Long id) {
        var foundAuthor = verifyAndGetAuthorBy(id);

        return authorMapper.toDTO(foundAuthor);
    }

    public List<AuthorDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteBy(Long id) {
        verifyAndGetAuthorBy(id);
        authorRepository.deleteById(id);
    }

    private void verifyIfExists(String authorName) {
        authorRepository.findByName(authorName)
                .ifPresent(author -> {
                    throw new AuthorAlreadyExistsException(author.getName());
                });
    }

    private Author verifyAndGetAuthorBy(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }
}
