package com.challenge.userpostapi.infrastructure.persistence.repository.impl;

import com.challenge.userpostapi.domain.model.UserModel;
import com.challenge.userpostapi.domain.repository.UserRepositoryInterface;
import com.challenge.userpostapi.infrastructure.mapper.UserMapper;
import com.challenge.userpostapi.infrastructure.persistence.entity.UserEntity;
import com.challenge.userpostapi.infrastructure.persistence.repository.interfaces.UserEntityInterfaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public UserModel findById(Long id) {
        return this.userMapper.toUserModel(this.userEntityInterfaceRepository.findById(id).orElse(null));
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
}
