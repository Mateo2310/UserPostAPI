package com.challenge.userpostapi.infrastructure.mapper;

import com.challenge.userpostapi.application.dto.PostRequestDTO;
import com.challenge.userpostapi.application.dto.PostResponseDTO;
import com.challenge.userpostapi.domain.model.PostModel;
import com.challenge.userpostapi.infrastructure.persistence.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {

    PostModel toPostModel(PostEntity postEntity);
    PostEntity toPostEntity(PostModel postModel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PostModel toPostModel(PostRequestDTO postRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PostResponseDTO toPostDTO(PostModel postModel);
}
