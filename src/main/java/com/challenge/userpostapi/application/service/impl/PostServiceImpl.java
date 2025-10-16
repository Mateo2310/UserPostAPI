package com.challenge.userpostapi.application.service.impl;

import com.challenge.userpostapi.application.dto.PostRequestDTO;
import com.challenge.userpostapi.application.dto.PostResponseDTO;
import com.challenge.userpostapi.application.service.interfaces.PostServiceInterface;
import com.challenge.userpostapi.domain.exception.*;
import com.challenge.userpostapi.domain.model.PostModel;
import com.challenge.userpostapi.domain.model.UserModel;
import com.challenge.userpostapi.domain.repository.PostRepositoryInterface;
import com.challenge.userpostapi.infrastructure.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostServiceInterface {
    private final PostRepositoryInterface postRepository;
    private final PostMapper postMapper;
    
    @Override
    public void save(PostRequestDTO request, Long authorId) {
        try {
            PostModel postModel = this.postMapper.toPostModel(request);
            UserModel authorModel = new UserModel(authorId);
            postModel.setAuthor(authorModel);
            this.postRepository.save(postModel);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al guardar el post", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        }
    }

    @Override
    public void deleteByIdAndAuthorId(Long id, Long authorId) {
        try {
            this.postRepository.deleteByIdAndAuthorId(id, authorId);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al eliminar el post", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        }
    }

    @Override
    public PostResponseDTO findByIdAndAuthorId(Long id, Long authorId) {
        try {
            PostModel postModel = this.postRepository.findByIdAndAuthorId(id, authorId).orElseThrow(() -> new ResourceNotFoundException("El post no existe"));
            return this.postMapper.toPostDTO(postModel);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener el post", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        }
    }

    @Override
    public List<PostResponseDTO> findAllByAuthorId(Long authorId) {
        try {
            List<PostModel> postModels = this.postRepository.findAllByUserId(authorId);
            return postModels.stream()
                    .map(this.postMapper::toPostDTO).toList();
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener los posts", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        }
    }

    @Override
    public PostResponseDTO updateByAuthorId(Long id, Long authorId, PostRequestDTO requestDTO) {
        try {
            PostModel postModel = this.postRepository.findByIdAndAuthorId(id, authorId).orElseThrow(() -> new ResourceNotFoundException("El post no existe"));
            postModel.setTitle(requestDTO.getTitle());
            postModel.setDescription(requestDTO.getDescription());
            postModel.setImageUrl(requestDTO.getImageUrl());
            PostModel postSaved = this.postRepository.save(postModel);
            return this.postMapper.toPostDTO(postSaved);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al actualizar los posts", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        }
    }

    @Override
    public PostResponseDTO partialUpdateByAuthorId(Long id, Long authorId, Map<String, Object> partialUpdateDTO) {
        try {
            PostModel postModel = this.postRepository.findByIdAndAuthorId(id, authorId).orElseThrow(() -> new ResourceNotFoundException("El post no existe"));
            partialUpdateDTO.forEach((key, value) -> {
                switch (key) {
                    case "title" -> postModel.setTitle((String) value);
                    case "description" -> postModel.setDescription((String) value);
                    case "imageUrl" -> postModel.setImageUrl((String) value);
                    default -> throw new ValidationException("Campo invalido: " + key);
                }
            });
            PostModel updated = this.postRepository.save(postModel);
            return this.postMapper.toPostDTO(updated);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al actualizar los posts", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            this.postRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al eliminar los posts", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        }
    }

    @Override
    public PostResponseDTO findById(Long id) {
        try {
            PostModel postModel = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El post no existe"));
            return this.postMapper.toPostDTO(postModel);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener el post", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        }
    }

    @Override
    public List<PostResponseDTO> findAll() {
        try {
            List<PostModel> postModels = this.postRepository.findAll();
            return postModels.stream()
                    .map(this.postMapper::toPostDTO).toList();
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener los posts", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        }
    }

    @Override
    public PostResponseDTO update(Long id, PostRequestDTO requestDTO) {
        try {
            PostModel postModel = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El post no existe"));
            postModel.setTitle(requestDTO.getTitle());
            postModel.setDescription(requestDTO.getDescription());
            postModel.setImageUrl(requestDTO.getImageUrl());
            PostModel postSaved = this.postRepository.save(postModel);
            return this.postMapper.toPostDTO(postSaved);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al actualizar los posts", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        }
    }

    @Override
    public PostResponseDTO partialUpdate(Long id, Map<String, Object> partialUpdateDTO) {
        try {
            PostModel postModel = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El post no existe"));
            partialUpdateDTO.forEach((key, value) -> {
                switch (key) {
                    case "title" -> postModel.setTitle((String) value);
                    case "description" -> postModel.setDescription((String) value);
                    case "imageUrl" -> postModel.setImageUrl((String) value);
                    default -> throw new ValidationException("Campo invalido: " + key);
                }
            });

            PostModel updated = this.postRepository.save(postModel);
            return this.postMapper.toPostDTO(updated);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al actualizar los posts", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        }
    }
}
