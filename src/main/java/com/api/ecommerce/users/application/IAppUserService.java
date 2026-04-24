package com.api.ecommerce.users.application;

import com.api.ecommerce.users.dto.response.UserIdUsernameDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface IAppUserService {
    Page<UserIdUsernameDTO> findAllIdAndUsername(Pageable pageRequest);
}
