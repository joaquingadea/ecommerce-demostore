package com.api.ecommerce.users.application;

import com.api.ecommerce.cart.domain.Cart;
import com.api.ecommerce.shared.security.cookie.CookieService;
import com.api.ecommerce.shared.security.jwt.JwtService;
import com.api.ecommerce.shared.web.ApiResponse;
import com.api.ecommerce.users.domain.AppUser;
import com.api.ecommerce.users.domain.AppUserRole;
import com.api.ecommerce.users.domain.Role;
import com.api.ecommerce.users.dto.request.LoginRequestDTO;
import com.api.ecommerce.users.dto.request.RegisterRequestDTO;
import com.api.ecommerce.users.dto.response.LoginResponseDTO;
import com.api.ecommerce.users.dto.response.RegisterResponseDTO;
import com.api.ecommerce.users.dto.response.UserIdUsernameRoleDTO;
import com.api.ecommerce.users.infrastructure.persistence.IAppUserRepository;
import com.api.ecommerce.users.infrastructure.persistence.IRoleRepository;
import com.api.ecommerce.users.infrastructure.security.UserDetailsServiceImp;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
public class AppUserService implements IAppUserService{

    private final IAppUserRepository appUserRepository;
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;

    public AppUserService(IAppUserRepository appUserRepository, UserDetailsServiceImp userDetailsServiceImp, JwtService jwtService, CookieService cookieService, PasswordEncoder passwordEncoder, IRoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtService = jwtService;
        this.cookieService = cookieService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<UserIdUsernameRoleDTO> findAllIdAndUsernameAndRole(Pageable pageRequest,Long adminId) {
        return appUserRepository.findAllIdAndUsernameAndRole(pageRequest,adminId);
    }
    @Override
    public LoginResponseDTO loginUser(@Valid LoginRequestDTO request, HttpServletResponse response){
        Authentication authentication = userDetailsServiceImp.authenticate(request.username(),request.password());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.createToken(authentication);
        cookieService.addCookie("jwt",jwt,response,60*120);
        return new LoginResponseDTO("User authorized successfully!",
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
    }

    @Override
    public RegisterResponseDTO registerUser(@Valid RegisterRequestDTO request) {
        if (!request.password().equals(request.confirmPassword())){
            throw new BadCredentialsException("Invalid username or password!");
        }
        boolean exists = appUserRepository.existsByUsername(request.username());
        if (exists){
            throw new BadCredentialsException("Invalid username or password!");
        }

        AppUser newUser = new AppUser();
        Cart newUserCart = new Cart();

        newUserCart.setUser(newUser);
        newUser.setUserCart(newUserCart);

        newUser.setName(request.name());
        newUser.setLastName(request.lastName());
        newUser.setUsername(request.username());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setCredentialsNonExpired(true);
        newUser.setEnabled(true);
        newUser.setRole(AppUserRole.USER);

        appUserRepository.save(newUser);
        return new RegisterResponseDTO("Successfully created user!");
    }

    @Override
    public ApiResponse logoutUser(HttpServletResponse response) {
        cookieService.deleteCookie("jwt",response);
        return new ApiResponse("Successfully logout!");
    }

    @Override
    public void setAdmin(Long userId) {
        AppUser userRepo = appUserRepository.findById(userId).orElseThrow();
        userRepo.setRole(AppUserRole.ADMIN);
        Role admin = roleRepository.findByName("ADMIN").orElseThrow();
        userRepo.getRoleList().add(admin);
    }

    @Override
    public void revokeAdmin(Long userId) {
        AppUser userRepo = appUserRepository.findById(userId).orElseThrow();
        userRepo.setRole(AppUserRole.USER);
        Role admin = roleRepository.findByName("ADMIN").orElseThrow();
        userRepo.getRoleList().remove(admin);
    }
}
