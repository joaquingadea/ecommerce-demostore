package com.api.ecommerce.users.api;

import com.api.ecommerce.shared.security.cookie.CookieService;
import com.api.ecommerce.shared.web.ApiResponse;
import com.api.ecommerce.users.application.IAppUserService;
import com.api.ecommerce.users.dto.request.LoginRequestDTO;
import com.api.ecommerce.users.dto.request.RegisterRequestDTO;
import com.api.ecommerce.users.dto.response.LoginResponseDTO;
import com.api.ecommerce.users.dto.response.RegisterResponseDTO;
import com.api.ecommerce.users.infrastructure.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private IAppUserService userService;

    public AuthController(IAppUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request, HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.loginUser(request,response));
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.registerUser(request));
    }
    @GetMapping("/me")
    public ResponseEntity<Map<String,Object>> myInfo(Authentication authentication){
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Map<String, Object> response = new HashMap<>();

        response.put("userId",user.getId());
        response.put("username",user.getUsername());
        response.put("authorities",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.logoutUser(response));
    }
}
