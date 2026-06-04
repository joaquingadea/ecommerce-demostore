package com.api.ecommerce.users.application;

import com.api.ecommerce.users.domain.Role;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface IRoleService {
    Role create(Role role);
    List<Role> findAll();
}
