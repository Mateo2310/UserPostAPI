package com.challenge.userpostapi.application.service.impl;

import com.challenge.userpostapi.application.dto.RoleRequestDTO;
import com.challenge.userpostapi.application.dto.RoleResponseDTO;
import com.challenge.userpostapi.application.service.interfaces.RoleServiceInterface;
import com.challenge.userpostapi.domain.exception.BusinessException;
import com.challenge.userpostapi.domain.exception.DatabaseUnavailableException;
import com.challenge.userpostapi.domain.exception.UnexpectedException;
import com.challenge.userpostapi.domain.repository.RoleRepositoryInterface;
import com.challenge.userpostapi.infrastructure.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleServiceInterface {
    private final RoleRepositoryInterface roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public void save(RoleRequestDTO request) {
        try {
            this.roleRepository.save(this.roleMapper.toRoleModel(request));
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al guardar el rol", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        } catch (Exception e) {
            throw new UnexpectedException("Error inesperado al guardar el rol", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            this.roleRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al eliminar el rol", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        } catch (Exception e) {
            throw new UnexpectedException("Error inesperado al eliminar el rol", e);
        }
    }

    @Override
    public RoleResponseDTO findById(Long id) {
        try {
            return this.roleMapper.toRoleResponseDTO(this.roleRepository.findById(id));
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener el rol", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        } catch (Exception e) {
            throw new UnexpectedException("Error inesperado al obtener el rol", e);
        }
    }

    @Override
    public List<RoleResponseDTO> findAll() {
        try {
            return this.roleRepository.findAll().stream()
                    .map(this.roleMapper::toRoleResponseDTO)
                    .toList();
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener los roles", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        } catch (Exception e) {
            throw new UnexpectedException("Error inesperado al obtener los roles", e);
        }
    }
}
