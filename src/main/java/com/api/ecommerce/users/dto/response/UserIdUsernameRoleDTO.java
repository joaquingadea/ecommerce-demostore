package com.api.ecommerce.users.dto.response;

import com.api.ecommerce.users.domain.AppUserRole;

public interface UserIdUsernameRoleDTO {
    Long getId();
    String getUsername();
    String getRole();
}
