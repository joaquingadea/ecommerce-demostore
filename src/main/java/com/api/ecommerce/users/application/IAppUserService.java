package com.api.ecommerce.users.application;

import com.api.ecommerce.shared.web.ApiResponse;
import com.api.ecommerce.users.dto.request.LoginRequestDTO;
import com.api.ecommerce.users.dto.request.RegisterRequestDTO;
import com.api.ecommerce.users.dto.response.LoginResponseDTO;
import com.api.ecommerce.users.dto.response.RegisterResponseDTO;
import com.api.ecommerce.users.dto.response.UserIdUsernameRoleDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAppUserService {
    Page<UserIdUsernameRoleDTO> findAllIdAndUsernameAndRole(Pageable pageRequest,Long adminId);
    LoginResponseDTO loginUser(LoginRequestDTO request, HttpServletResponse response);
    RegisterResponseDTO registerUser(RegisterRequestDTO request);
    ApiResponse logoutUser(HttpServletResponse response);
    void setAdmin(Long userId);
    void revokeAdmin(Long userId);
}
