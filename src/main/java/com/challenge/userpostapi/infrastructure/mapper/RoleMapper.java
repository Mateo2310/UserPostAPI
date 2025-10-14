package com.challenge.userpostapi.infrastructure.mapper;

import com.challenge.userpostapi.application.dto.RoleRequestDTO;
import com.challenge.userpostapi.application.dto.RoleResponseDTO;
import com.challenge.userpostapi.domain.model.RoleModel;
import com.challenge.userpostapi.infrastructure.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {
    RoleModel toRoleModel(RoleEntity roleEntity);

    @Mapping(target = "users", ignore = true)
    RoleEntity toRoleEntity(RoleModel roleModel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    RoleModel toRoleModel(RoleRequestDTO roleRequestDTO);

    RoleResponseDTO toRoleResponseDTO(RoleModel roleModel);
}
