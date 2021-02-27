package com.wwwgomes.bookstoremanager.api.mapper;

import com.wwwgomes.bookstoremanager.api.dto.UserDTO;
import com.wwwgomes.bookstoremanager.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toModel(UserDTO userDTO);

    UserDTO toDTO(User user);
}
