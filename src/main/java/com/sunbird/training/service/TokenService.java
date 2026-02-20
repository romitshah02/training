package com.sunbird.training.service;


import java.time.Instant;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.sunbird.training.entity.User;
import com.sunbird.training.enums.Role;

@Service
public class TokenService {

    private JwtEncoder jwtEncoder;

  
    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }


    public Role determineRole(String username){
        Role role  = Role.USER;

        if (username.contains("@superadmin.com")){
            role = Role.SUPERADMIN;
        }

        
        if (username.contains("@admin.com")){
            role = Role.ADMIN;
        }

        return role;
    }

    public String generateToken(User user){
   
        JwtClaimsSet claims = JwtClaimsSet.builder()
                                .claim("role", user.getRole())
                                .expiresAt(Instant.now().plusSeconds(3600))
                                .build();


        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        }


  

}
