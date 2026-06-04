package com.api.ecommerce.shared.security.jwt;

import com.api.ecommerce.users.infrastructure.security.CustomUserDetails;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.private.key}")
    private String privateKey;
    @Value("${jwt.user.generator}")
    private String userGenerator;

    public String createToken(Authentication authentication){
        Algorithm algorithm = Algorithm.HMAC256(privateKey);

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        List<String> autorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return JWT.create()
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + (60 * 60000)))
                .withSubject(user.getUsername())
                .withClaim("authorities", autorities)
                .withClaim("userId",user.getId())
                .withJWTId(UUID.randomUUID().toString())
                .withIssuer(userGenerator)
                .sign(algorithm);
    }

    public DecodedJWT decodeJwt(String jwt) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();
            return verifier.verify(jwt);
        }
        catch (JWTVerificationException e){
            throw new JWTVerificationException("Invalid token!");
        }
    }

    public Claim getSpecificClaim(String name,DecodedJWT decodedJWT){
        return decodedJWT.getClaim(name);
    }
}
