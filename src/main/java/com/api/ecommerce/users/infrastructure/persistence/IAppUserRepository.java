package com.api.ecommerce.users.infrastructure.persistence;

import com.api.ecommerce.users.domain.AppUser;
import com.api.ecommerce.users.dto.response.UserIdUsernameRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAppUserRepository extends JpaRepository<AppUser,Long>, JpaSpecificationExecutor<AppUser> {
    @Query("""
        SELECT u.id AS id, u.username AS username, u.role AS role
        FROM AppUser u
        WHERE u.id <> :adminId
    """)
    Page<UserIdUsernameRoleDTO> findAllIdAndUsernameAndRole(Pageable pageRequest, @Param("adminId")Long adminId);
    Optional<AppUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
