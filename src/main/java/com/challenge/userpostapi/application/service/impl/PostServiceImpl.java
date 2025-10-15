package com.challenge.userpostapi.application.service.impl;

import com.challenge.userpostapi.application.dto.PostRequestDTO;
import com.challenge.userpostapi.application.dto.PostResponseDTO;
import com.challenge.userpostapi.application.service.interfaces.PostServiceInterface;
import com.challenge.userpostapi.domain.exception.BusinessException;
import com.challenge.userpostapi.domain.exception.DatabaseUnavailableException;
import com.challenge.userpostapi.domain.exception.UnexpectedException;
import com.challenge.userpostapi.domain.model.PostModel;
import com.challenge.userpostapi.domain.model.UserModel;
import com.challenge.userpostapi.domain.repository.PostRepositoryInterface;
import com.challenge.userpostapi.infrastructure.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.List;

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
        } catch (Exception e) {
            throw new UnexpectedException("Error inesperado al guardar el post", e);
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
        } catch (Exception e) {
            throw new UnexpectedException("Error inesperado al eliminar el post", e);
        }
    }

    @Override
    public PostResponseDTO findByIdAndAuthorId(Long id, Long authorId) {
        try {
            PostModel postModel = this.postRepository.findByIdAndAuthorId(id, authorId);
            return this.postMapper.toPostDTO(postModel);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener el post", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        } catch (Exception e) {
            throw new UnexpectedException("Error inesperado al obtener el post", e);
        }
    }

    @Override
    public List<PostResponseDTO> findAll(Long authorId) {
        try {
            List<PostModel> postModels = this.postRepository.findAllByUserId(authorId);
            return postModels.stream()
                    .map(this.postMapper::toPostDTO).toList();
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener los posts", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        } catch (Exception e) {
            throw new UnexpectedException("Error inesperado al obtener los posts", e);
        }
    }
}
