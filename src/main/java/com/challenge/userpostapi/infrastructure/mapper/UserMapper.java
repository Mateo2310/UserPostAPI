package com.challenge.userpostapi.infrastructure.mapper;

import com.challenge.userpostapi.application.dto.UserRequestDTO;
import com.challenge.userpostapi.application.dto.UserResponseDTO;
import com.challenge.userpostapi.domain.model.UserModel;
import com.challenge.userpostapi.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = {RoleMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mapping(source = "roleEntity", target = "roleModel")
    UserModel toUserModel(UserEntity userEntity);

    @Mapping(target = "posts", ignore = true)
    @Mapping(source = "roleModel", target = "roleEntity")
    UserEntity toUserEntity(UserModel userModel);

    @Mapping(source = "roleId", target = "roleModel.id")
    UserModel toUserModel(UserRequestDTO userRequestDTO);

    @Mapping(source = "roleModel", target = "role")
    UserResponseDTO toUserResponseDTO(UserModel userModel);
}
