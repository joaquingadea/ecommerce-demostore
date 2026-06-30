package com.api.ecommerce.users.api;

import com.api.ecommerce.shared.security.jwt.JwtPrincipal;
import com.api.ecommerce.users.application.IAppUserService;
import com.api.ecommerce.users.application.IPermissionService;
import com.api.ecommerce.users.application.IRoleService;
import com.api.ecommerce.users.domain.Permission;
import com.api.ecommerce.users.domain.Role;
import com.api.ecommerce.users.dto.response.UserIdUsernameRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {

    private final IAppUserService appUserService;
    private final IRoleService roleService;
    private final IPermissionService permissionService;

    public AdminUserController(IAppUserService appUserService, IRoleService roleService, IPermissionService permissionService) {
        this.appUserService = appUserService;
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @GetMapping("/customer-list")
    public ResponseEntity<Page<UserIdUsernameRoleDTO>> getCustomers(@PageableDefault(size = 10,page = 0) Pageable pageable,
                                                                    @AuthenticationPrincipal JwtPrincipal auth){
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize());
        return ResponseEntity.status(HttpStatus.OK)
                .body(appUserService.findAllIdAndUsernameAndRole(pageRequest,auth.userId()));
    }

    @PatchMapping("/revoke-admin/{userId}")
    public ResponseEntity<Void> revokeAdmin(@PathVariable Long userId){
        appUserService.revokeAdmin(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PatchMapping("/set-admin/{userId}")
    public ResponseEntity<Void> setAdmin(@PathVariable Long userId){
        appUserService.setAdmin(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/get-roles")
    public ResponseEntity<List<Role>> getRoles(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(roleService.findAll());
    }
    @PostMapping("/create-role")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.create(role));
    }
    @GetMapping("/get-permissions")
    public ResponseEntity<List<Permission>> getPermissions(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(permissionService.findAll());
    }
    @PostMapping("/create-permission")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(permissionService.create(permission));
    }
}
