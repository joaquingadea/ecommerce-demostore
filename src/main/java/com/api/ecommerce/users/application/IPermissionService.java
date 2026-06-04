package com.api.ecommerce.users.application;

import com.api.ecommerce.users.domain.Permission;
import com.api.ecommerce.users.domain.Role;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface IPermissionService {
    Permission create(Permission permission);
    List<Permission> findAll();
}
