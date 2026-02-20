package com.sunbird.training.config;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;



@Configuration
public class KeyConfig {

    @Value("${key.private}")
    private Resource privateKeyResource;

    @Value("${key.public}")
    private Resource publicKeyResource;

    @Bean
    RSAPrivateKey rsaPrivateKey() throws Exception {

        String key = new String(privateKeyResource.getInputStream().readAllBytes())
                            .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                            .replaceAll(System.lineSeparator(), "")
                            .replace("-----END PRIVATE KEY-----", "")
                            .replaceAll("\\s", "");

        byte[] encode = Base64.getDecoder().decode(key);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encode);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        return (RSAPrivateKey) kf.generatePrivate(spec);
    }

    @Bean
    RSAPublicKey rsaPublicKey() throws Exception {
        String key = new String(publicKeyResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        
        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        
        // X509 is the standard for Public Keys
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(keySpec);
    }



}
