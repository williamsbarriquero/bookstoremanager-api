package com.wwwgomes.bookstoremanager.api.mapper;

import com.wwwgomes.bookstoremanager.api.dto.AuthorDTO;
import com.wwwgomes.bookstoremanager.domain.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toModel(AuthorDTO authorDTO);

    AuthorDTO toDTO(Author author);
}
