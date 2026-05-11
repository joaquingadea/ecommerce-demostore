package com.api.ecommerce.users.application;

import com.api.ecommerce.shared.web.ApiResponse;
import com.api.ecommerce.users.dto.request.LoginRequestDTO;
import com.api.ecommerce.users.dto.request.RegisterRequestDTO;
import com.api.ecommerce.users.dto.response.LoginResponseDTO;
import com.api.ecommerce.users.dto.response.RegisterResponseDTO;
import com.api.ecommerce.users.dto.response.UserIdUsernameDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAppUserService {
    Page<UserIdUsernameDTO> findAllIdAndUsername(Pageable pageRequest);
    LoginResponseDTO loginUser(LoginRequestDTO request, HttpServletResponse response);
    RegisterResponseDTO registerUser(RegisterRequestDTO request);
    ApiResponse logoutUser(HttpServletResponse response);
}
