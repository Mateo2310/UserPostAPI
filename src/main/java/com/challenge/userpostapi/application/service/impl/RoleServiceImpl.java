package com.challenge.userpostapi.application.service.impl;

import com.challenge.userpostapi.application.dto.RoleRequestDTO;
import com.challenge.userpostapi.application.dto.RoleResponseDTO;
import com.challenge.userpostapi.application.service.interfaces.RoleServiceInterface;
import com.challenge.userpostapi.domain.enums.RoleEnum;
import com.challenge.userpostapi.domain.exception.*;
import com.challenge.userpostapi.domain.model.RoleModel;
import com.challenge.userpostapi.domain.repository.RoleRepositoryInterface;
import com.challenge.userpostapi.infrastructure.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleServiceInterface {
    private final RoleRepositoryInterface roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public void save(RoleRequestDTO request) {
        try {
            request.setName(request.getName());
            this.roleRepository.save(this.roleMapper.toRoleModel(request));
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al guardar el rol", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
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
        }
    }

    @Override
    public RoleResponseDTO findById(Long id) {
        try {
            RoleModel roleModel = this.roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El rol no existe"));
            return this.roleMapper.toRoleResponseDTO(roleModel);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener el rol", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
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
        }
    }

    @Override
    public RoleResponseDTO update(Long id, RoleRequestDTO requestDTO) {
        try {
            RoleModel roleModel = this.roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El rol no existe"));
            roleModel.setName(requestDTO.getName());
            RoleModel roleSaved = this.roleRepository.save(roleModel);
            return this.roleMapper.toRoleResponseDTO(roleSaved);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener el rol", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        }
    }

    @Override
    public RoleResponseDTO partialUpdate(Long id, Map<String, Object> partialUpdateDTO) {
        try {
            RoleModel roleModel = this.roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El rol no existe"));
            partialUpdateDTO.forEach((key, value) -> {
                if (key.equals("name")) {
                    roleModel.setName(RoleEnum.valueOf((String) value));
                } else {
                    throw new ValidationException("Campo invalido: " + key);
                }
            });
            RoleModel roleSaved = this.roleRepository.save(roleModel);
            return this.roleMapper.toRoleResponseDTO(roleSaved);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener el rol", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        }
    }
}
