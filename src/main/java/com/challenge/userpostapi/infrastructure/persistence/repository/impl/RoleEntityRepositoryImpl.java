package com.challenge.userpostapi.infrastructure.persistence.repository.impl;

import com.challenge.userpostapi.domain.model.RoleModel;
import com.challenge.userpostapi.domain.repository.RoleRepositoryInterface;
import com.challenge.userpostapi.infrastructure.mapper.RoleMapper;
import com.challenge.userpostapi.infrastructure.persistence.entity.RoleEntity;
import com.challenge.userpostapi.infrastructure.persistence.repository.interfaces.RoleEntityInterfaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleEntityRepositoryImpl implements RoleRepositoryInterface {
    private final RoleEntityInterfaceRepository roleEntityInterfaceRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleModel save(RoleModel model) {
        RoleEntity roleSaved = this.roleEntityInterfaceRepository.save(this.roleMapper.toRoleEntity(model));
        return this.roleMapper.toRoleModel(roleSaved);
    }

    @Override
    public Optional<RoleModel> findById(Long id) {
        RoleModel roleModel = this.roleMapper.toRoleModel(this.roleEntityInterfaceRepository.findById(id).orElse(null));
        return Optional.ofNullable(roleModel);
    }

    @Override
    public List<RoleModel> findAll() {
        return this.roleEntityInterfaceRepository.findAll().stream()
                .map(this.roleMapper::toRoleModel)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        this.roleEntityInterfaceRepository.deleteById(id);
    }

    @Override
    public Boolean existsByName(String name) {
        return this.roleEntityInterfaceRepository.existsRoleEntityByName(name);
    }
}
