package com.msashop.auth.authr.userrolemap.adapter.out.persistence.mapper;

import org.springframework.stereotype.Component;

import com.msashop.auth.authr.role.adapter.out.persistence.entity.RoleEntity;
import com.msashop.auth.authr.userrolemap.adapter.out.persistence.entity.UserRoleMapEntity;
import com.msashop.auth.authr.userrolemap.adapter.out.persistence.entity.UserRoleMapEntityId;
import com.msashop.auth.authr.userrolemap.domain.model.UserRoleMap;
import com.msashop.auth.authr.userrolemap.domain.model.UserRoleMapId;
import com.msashop.auth.identity.user.adapter.out.persistence.entity.UserEntity;

@Component
public class UserRoleMapEntityMapper {

    public UserRoleMapEntity toEntity(UserRoleMap domain) {
        UserRoleMapEntity entity = new UserRoleMapEntity();
        UserRoleMapEntityId id = toEntityId(domain.getId());
        entity.setId(id);
        entity.setUserId(domain.getUserId());
        entity.setRoleId(domain.getRoleId());

        UserEntity userRef = new UserEntity();
        userRef.setUserId(domain.getUserId());
        entity.setUser(userRef);

        RoleEntity roleRef = new RoleEntity();
        roleRef.setRoleId(domain.getRoleId());
        entity.setRole(roleRef);
        return entity;
    }

    public UserRoleMap toDomain(UserRoleMapEntity entity) {
        UserRoleMapId id = UserRoleMapId.of(entity.getUserId(), entity.getRoleId());
        return UserRoleMap.of(id.userId(), id.roleId());
    }

    public UserRoleMapEntityId toEntityId(UserRoleMapId id) {
        return new UserRoleMapEntityId(id.userId(), id.roleId());
    }
}
