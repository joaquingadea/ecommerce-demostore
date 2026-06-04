package com.api.ecommerce.shared.security.jwt;

import com.api.ecommerce.users.infrastructure.security.CustomUserDetails;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    private JwtService jwtService;

    public JwtTokenValidator(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        if (
                path.equals("/auth/login") ||
                        path.equals("/auth/register")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = getJwt(request);
        try {
            if (jwt != null) {
                DecodedJWT decodedJWT = jwtService.decodeJwt(jwt);
                Long userId = decodedJWT.getClaim("userId").asLong();
                String username = decodedJWT.getSubject();
                List<String> authorities = decodedJWT.getClaim("authorities")
                        .asList(String.class);

                if (authorities == null){
                    throw new RuntimeException("Not found authorities!");
                }

                Collection<? extends GrantedAuthority> authorityList =
                        authorities.stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList();

                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                new JwtPrincipal(userId, username),
                                null,
                                authorityList
                        )
                );
                SecurityContextHolder.setContext(context);
            }
        }catch (Exception e) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request,response);
    }
    public String getJwt(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request,"jwt");
        return cookie != null ? cookie.getValue() : null;
    }
}
