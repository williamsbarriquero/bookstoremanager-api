package com.wwwgomes.bookstoremanager.api.resource;

import com.wwwgomes.bookstoremanager.api.dto.AuthorDTO;
import com.wwwgomes.bookstoremanager.api.resource.docs.AuthorResourceDocs;
import com.wwwgomes.bookstoremanager.api.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorResource implements AuthorResourceDocs {

    private final AuthorService authorService;

    @Autowired
    public AuthorResource(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public AuthorDTO create(@RequestBody @Valid AuthorDTO authorDTO) {
        return authorService.create(authorDTO);
    }
}
