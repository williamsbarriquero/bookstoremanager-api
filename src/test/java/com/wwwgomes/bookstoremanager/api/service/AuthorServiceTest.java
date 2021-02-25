package com.wwwgomes.bookstoremanager.api.service;

import com.wwwgomes.bookstoremanager.api.builder.AuthorDTOBuilder;
import com.wwwgomes.bookstoremanager.api.mapper.AuthorMapper;
import com.wwwgomes.bookstoremanager.domain.repository.AuthorRepository;
import com.wwwgomes.bookstoremanager.exception.AuthorAlreadyExistsException;
import com.wwwgomes.bookstoremanager.exception.AuthorNotFoundException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private AuthorDTOBuilder authorDTOBuilder;

    @BeforeEach
    void setUp() {
        authorDTOBuilder = AuthorDTOBuilder.builder().build();
    }

    @Test
    void whenNewAuthorIsInformedThenItShouldBeCreated() {
        // given
        var expectedAuthorToCreateDTO = authorDTOBuilder.buildAuthorDTO();
        var expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        // when
        Mockito.when(authorRepository.save(expectedCreatedAuthor)).thenReturn(expectedCreatedAuthor);
        Mockito.when(authorRepository.findByName(expectedAuthorToCreateDTO.getName())).thenReturn(Optional.empty());

        var createdAuthorDTO = authorService.create(expectedAuthorToCreateDTO);

        // then
        MatcherAssert.assertThat(createdAuthorDTO, Is.is(IsEqual.equalTo(expectedAuthorToCreateDTO)));
    }

    @Test
    void whenExistingAuthorIsInformedThenAnExceptionItShouldBeThrown() {
        var expectedAuthorToCreateDTO = authorDTOBuilder.buildAuthorDTO();
        var expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        Mockito.when(authorRepository.findByName(expectedAuthorToCreateDTO.getName()))
                .thenReturn(Optional.of(expectedCreatedAuthor));

        Assertions.assertThrows(AuthorAlreadyExistsException.class,
                () -> authorService.create(expectedAuthorToCreateDTO));
    }

    @Test
    void whenValidIdIsGivenThenAnAuthorShouldBeReturned() {
        var expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();
        var expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDTO);

        Mockito.when(authorRepository.findById(expectedFoundAuthorDTO.getId()))
                .thenReturn(Optional.of(expectedFoundAuthor));

        var foundAuthorDTO = authorService.findById(expectedFoundAuthorDTO.getId());

        MatcherAssert.assertThat(foundAuthorDTO, Is.is(IsEqual.equalTo(expectedFoundAuthorDTO)));
    }

    @Test
    void whenInvalidIdIsGivenThenAnExceptionShouldBeThrown() {
        var expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();

        Mockito.when(authorRepository.findById(expectedFoundAuthorDTO.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(AuthorNotFoundException.class, () -> authorService.findById(expectedFoundAuthorDTO.getId()));
    }

    @Test
    void whenListAuthorsIsCalledThenItShouldBeReturned() {
        var expectedFoundAuthorDTO = authorDTOBuilder.buildAuthorDTO();
        var expectedFoundAuthor = authorMapper.toModel(expectedFoundAuthorDTO);

        Mockito.when(authorRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundAuthor));

        var foundAuthorsDTO = authorService.findAll();

        MatcherAssert.assertThat(foundAuthorsDTO.size(), Is.is(1));
        MatcherAssert.assertThat(foundAuthorsDTO.get(0), Is.is(IsEqual.equalTo(expectedFoundAuthorDTO)));
    }

    @Test
    void whenListAuthorsIsCalledThenAnEmptyListShouldBeReturned() {
        Mockito.when(authorRepository.findAll()).thenReturn(List.of());

        var foundAuthorsDTO = authorService.findAll();

        MatcherAssert.assertThat(foundAuthorsDTO.size(), Is.is(0));
    }
}
