package com.api.ecommerce.users.application;

import com.api.ecommerce.users.dto.response.UserIdUsernameDTO;
import com.api.ecommerce.users.infrastructure.persistence.IAppUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements IAppUserService{

    private IAppUserRepository appUserRepository;

    public AppUserService(IAppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Page<UserIdUsernameDTO> findAllIdAndUsername(Pageable pageRequest) {
        return appUserRepository.findAllIdAndUsername(pageRequest);
    }
}
