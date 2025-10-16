package com.challenge.userpostapi.infrastructure.persistence.repository.impl;

import com.challenge.userpostapi.domain.model.UserModel;
import com.challenge.userpostapi.domain.repository.UserRepositoryInterface;
import com.challenge.userpostapi.infrastructure.mapper.UserMapper;
import com.challenge.userpostapi.infrastructure.persistence.entity.UserEntity;
import com.challenge.userpostapi.infrastructure.persistence.repository.interfaces.UserEntityInterfaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserEntityRepositoryImpl implements UserRepositoryInterface {
    private final UserEntityInterfaceRepository userEntityInterfaceRepository;
    private final UserMapper userMapper;

    @Override
    public UserModel save(UserModel model) {
        UserEntity userSaved = this.userEntityInterfaceRepository.save(this.userMapper.toUserEntity(model));
        return this.userMapper.toUserModel(userSaved);
    }

    @Override
    public Optional<UserModel> findById(Long id) {
        UserModel userModel = this.userMapper.toUserModel(this.userEntityInterfaceRepository.findById(id).orElse(null));
        return Optional.ofNullable(userModel);
    }

    @Override
    public List<UserModel> findAll() {
        return this.userEntityInterfaceRepository.findAll().stream()
                .map(this.userMapper::toUserModel)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        this.userEntityInterfaceRepository.deleteById(id);
    }

    @Override
    public UserModel findByUsername(String username) {
        return this.userEntityInterfaceRepository.findByUsername(username).map(this.userMapper::toUserModel).orElse(null);
    }
}
