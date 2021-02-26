package com.wwwgomes.bookstoremanager.api.service;

import com.wwwgomes.bookstoremanager.api.builder.PublisherDTOBuilder;
import com.wwwgomes.bookstoremanager.api.dto.PublisherDTO;
import com.wwwgomes.bookstoremanager.api.mapper.PublisherMapper;
import com.wwwgomes.bookstoremanager.domain.entity.Publisher;
import com.wwwgomes.bookstoremanager.domain.repository.PublisherRepository;
import com.wwwgomes.bookstoremanager.exception.PublisherAlreadyExistsException;
import com.wwwgomes.bookstoremanager.exception.PublisherNotFoundException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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
public class PublisherServiceTest {

    private final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
    }

    @Test
    void whenNewPublisherIsInformedThenItShouldBeCreated() {
        PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherCreated = publisherMapper.toModel(expectedPublisherToCreateDTO);

        Mockito.when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
                .thenReturn(Optional.empty());
        Mockito.when(publisherRepository.save(expectedPublisherCreated)).thenReturn(expectedPublisherCreated);

        PublisherDTO createdPublisherDTO = publisherService.create(expectedPublisherToCreateDTO);

        MatcherAssert.assertThat(createdPublisherDTO, Matchers.is(IsEqual.equalTo(expectedPublisherToCreateDTO)));
    }

    @Test
    void whenExistingPublisherIsInformedThenAnExceptionShouldBeThrown() {
        PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherDuplicated = publisherMapper.toModel(expectedPublisherToCreateDTO);

        Mockito.when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
                .thenReturn(Optional.of(expectedPublisherDuplicated));

        Assertions.assertThrows(PublisherAlreadyExistsException.class, () -> publisherService.create(expectedPublisherToCreateDTO));
    }

    @Test
    void whenValidIdIsGivenThenAPublisherShouldBeReturned() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherFound = publisherMapper.toModel(expectedPublisherFoundDTO);
        var expectedPublisherFoundId = expectedPublisherFoundDTO.getId();

        Mockito.when(publisherRepository.findById(expectedPublisherFoundId)).thenReturn(Optional.of(expectedPublisherFound));

        PublisherDTO foundPublisherDTO = publisherService.findById(expectedPublisherFoundId);

        MatcherAssert.assertThat(foundPublisherDTO, Is.is(IsEqual.equalTo(foundPublisherDTO)));
    }

    @Test
    void whenInvalidIdIsGivenThenAnExceptionShouldBeThrown() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        var expectedPublisherFoundId = expectedPublisherFoundDTO.getId();

        Mockito.when(publisherRepository.findById(expectedPublisherFoundId)).thenReturn(Optional.empty());

        Assertions.assertThrows(PublisherNotFoundException.class, () -> publisherService.findById(expectedPublisherFoundId));
    }

    @Test
    void whenListPublishersIsCalledThenItShouldBeReturned() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherFound = publisherMapper.toModel(expectedPublisherFoundDTO);

        Mockito.when(publisherRepository.findAll()).thenReturn(Collections.singletonList(expectedPublisherFound));

        List<PublisherDTO> foundPublishersDTO = publisherService.findAll();

        MatcherAssert.assertThat(foundPublishersDTO.size(), Is.is(1));
        MatcherAssert.assertThat(foundPublishersDTO.get(0), Is.is(IsEqual.equalTo(expectedPublisherFoundDTO)));
    }

    @Test
    void whenListPublishersIsCalledThenAnEmptyListShouldBeReturned() {
        Mockito.when(publisherRepository.findAll()).thenReturn(List.of());

        List<PublisherDTO> foundPublishersDTO = publisherService.findAll();

        MatcherAssert.assertThat(foundPublishersDTO.size(), Is.is(0));
    }

    @Test
    void whenValidPublisherIdIsGivenThenItShouldBeDeleted() {
        PublisherDTO expectedPublisherDeletedDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherDeleted = publisherMapper.toModel(expectedPublisherDeletedDTO);

        var expectedDeletedPublisherId = expectedPublisherDeletedDTO.getId();
        Mockito.doNothing().when(publisherRepository).deleteById(expectedDeletedPublisherId);
        Mockito.when(publisherRepository.findById(expectedDeletedPublisherId)).thenReturn(Optional.of(expectedPublisherDeleted));

        publisherService.delete(expectedDeletedPublisherId);

        Mockito.verify(publisherRepository, Mockito.times(1)).deleteById(expectedDeletedPublisherId);
    }

    @Test
    void whenInvalidPublisherIdIsGivenThenItShouldNotBeDeleted() {
        var expectedInvalidPublisherId = 2L;

        Mockito.when(publisherRepository.findById(expectedInvalidPublisherId)).thenReturn(Optional.empty());

        Assertions.assertThrows(PublisherNotFoundException.class, () -> publisherService.delete(expectedInvalidPublisherId));
    }
}