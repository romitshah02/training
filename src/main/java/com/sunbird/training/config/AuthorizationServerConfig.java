package com.sunbird.training.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class AuthorizationServerConfig {

    private final RSAPrivateKey rsaPrivateKey;

    private final RSAPublicKey rsaPublicKey;


    AuthorizationServerConfig(RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {
        this.rsaPublicKey = rsaPublicKey;
        this.rsaPrivateKey = rsaPrivateKey;
    }
    
    @Bean
    JwtEncoder jwtEncoder()  throws Exception{

        return NimbusJwtEncoder.withKeyPair(rsaPublicKey, rsaPrivateKey).build();
    }   


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
   
   
}
