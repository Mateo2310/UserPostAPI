package com.challenge.userpostapi.application.service.impl;

import com.challenge.userpostapi.application.dto.UserRequestDTO;
import com.challenge.userpostapi.application.dto.UserResponseDTO;
import com.challenge.userpostapi.application.service.interfaces.UserServiceInterface;
import com.challenge.userpostapi.domain.exception.BusinessException;
import com.challenge.userpostapi.domain.exception.DatabaseUnavailableException;
import com.challenge.userpostapi.domain.exception.UnexpectedException;
import com.challenge.userpostapi.domain.model.RoleModel;
import com.challenge.userpostapi.domain.model.UserModel;
import com.challenge.userpostapi.domain.repository.RoleRepositoryInterface;
import com.challenge.userpostapi.domain.repository.UserRepositoryInterface;
import com.challenge.userpostapi.infrastructure.mapper.UserMapper;
import com.challenge.userpostapi.infrastructure.security.JwtService;
import com.challenge.userpostapi.infrastructure.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceInterface {
    private final UserRepositoryInterface userRepository;
    private final UserMapper userMapper;
    private final RoleRepositoryInterface roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String save(UserRequestDTO request) {
        try {
            RoleModel roleModel = this.roleRepository.findById(request.getRoleId());
            if(roleModel == null){
                throw new BusinessException("Role Not Found. Id: " + request.getRoleId());
            }
            UserModel userModel = this.userMapper.toUserModel(request);
            userModel.setPassword(passwordEncoder.encode(request.getPassword()));
            userModel.setRoleModel(roleModel);
            UserModel userModelSaved = this.userRepository.save(userModel);
            return this.jwtService.generateToken(new UserDetailsImpl(userModelSaved));
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al guardar el usuario", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        } catch (Exception e) {
            throw new UnexpectedException("Error inesperado al guardar el usuario", e);
        }
    }

    @Override
    public UserModel findByUsername(String username) {
        try {
            return this.userRepository.findByUsername(username);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener el usuario", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        } catch (Exception e) {
            throw new UnexpectedException("Error inesperado al obtener el usuario", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            this.userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al eliminar el usuario", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        } catch (Exception e) {
            throw new UnexpectedException("Error inesperado al eliminar el usuario", e);
        }
    }

    @Override
    public UserResponseDTO findById(Long id) {
        try {
            UserModel userModel = this.userRepository.findById(id);
            return this.userMapper.toUserResponseDTO(userModel);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener el usuario", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        } catch (Exception e) {
            throw new UnexpectedException("Error inesperado al obtener el usuario", e);
        }
    }

    @Override
    public List<UserResponseDTO> findAll() {
        try {
            List<UserModel> userModels = this.userRepository.findAll();
            return userModels.stream()
                    .map(this.userMapper::toUserResponseDTO).toList();
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Violación de integridad al obtener los usuarios", e);
        } catch (CannotCreateTransactionException cctex) {
            throw new DatabaseUnavailableException("No se pudo conectar con la base de datos", cctex);
        } catch (Exception e) {
            throw new UnexpectedException("Error inesperado al obtener los usuarios", e);
        }
    }
}
