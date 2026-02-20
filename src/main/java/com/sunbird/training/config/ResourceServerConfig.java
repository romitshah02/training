package com.sunbird.training.config;


import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;


@Configuration
public class ResourceServerConfig {

    private final RSAPublicKey rsaPublicKey;
    private final HandlerExceptionResolver resolver;




    ResourceServerConfig(RSAPublicKey rsaPublicKey,@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.rsaPublicKey = rsaPublicKey;
        this.resolver = resolver;
    }

    @Bean
    JwtDecoder jwtDecoder()  throws Exception{
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }   


    @Bean 
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
                                                    .requestMatchers(HttpMethod.POST,"/api/**").hasAnyRole("SUPERADMIN","ADMIN")
                                                    .requestMatchers(HttpMethod.PUT,"/api/**").hasRole("SUPERADMIN")
                                                    .requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("SUPERADMIN")
                                                    .anyRequest().authenticated()
                                    )
                .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(jwt -> jwt.jwtAuthenticationConverter(customConverter())
                )
                .authenticationEntryPoint((request,response,authException) ->
                    resolver.resolveException(request, response, null, authException))
                 .accessDeniedHandler((request, response, accessDeniedException) -> 
                    resolver.resolveException(request, response, null, accessDeniedException))
            );                                             

        return http.build();
    }

    @Bean 
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
    }


    @Bean
    JwtAuthenticationConverter customConverter(){
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("role");
        authoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }   

}
