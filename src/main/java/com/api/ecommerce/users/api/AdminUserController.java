package com.api.ecommerce.users.api;

import com.api.ecommerce.users.application.IAppUserService;
import com.api.ecommerce.users.dto.response.UserIdUsernameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private IAppUserService appUserService;

    public AdminUserController(IAppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/customer-list") // AdminUserController
    public ResponseEntity<Page<UserIdUsernameDTO>> getCustomers(@PageableDefault(size = 10,page = 0) Pageable pageable){
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize());
        return ResponseEntity.status(HttpStatus.OK)
                .body(appUserService.findAllIdAndUsername(pageRequest));
    }
}
