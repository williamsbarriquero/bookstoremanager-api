package com.wwwgomes.bookstoremanager.api.mapper;

import com.wwwgomes.bookstoremanager.api.dto.BookRequestDTO;
import com.wwwgomes.bookstoremanager.api.dto.BookResponseDTO;
import com.wwwgomes.bookstoremanager.domain.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toModel(BookRequestDTO bookRequestDTO);

    Book toModel(BookResponseDTO bookRequestDTO);

    BookResponseDTO toDTO(Book book);
}
