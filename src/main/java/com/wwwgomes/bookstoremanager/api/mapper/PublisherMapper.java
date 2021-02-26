package com.wwwgomes.bookstoremanager.api.mapper;

import com.wwwgomes.bookstoremanager.api.dto.PublisherDTO;
import com.wwwgomes.bookstoremanager.domain.entity.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    Publisher toModel(PublisherDTO publisherDTO);

    PublisherDTO toDTO(Publisher publisher);
}