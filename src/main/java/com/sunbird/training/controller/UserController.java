package com.sunbird.training.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbird.training.dao.ApiResponse;
import com.sunbird.training.dao.ResponseParams;
import com.sunbird.training.entity.User;
import com.sunbird.training.service.TokenService;
import com.sunbird.training.service.UserService;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@RequestBody User user) {

        System.out.println("Checking..............");
        
        user.setRole(tokenService.determineRole(user.getUsername()));

        userService.signup(user);

        String token = tokenService.generateToken(user);

        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString()
            , "success"
            , null
            , null
        );

        ApiResponse<String> response = new ApiResponse<String>(
        "api.course.get",
        "v1",
        LocalDate.now().toString(), 
        params, 
        "OK", 
        token
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody User user) {
        String token = "";
        User found = userService.findByUsername(user.getUsername());


        if (found != null && passwordEncoder.matches(user.getPassword(), found.getPassword())){
            user.setRole(tokenService.determineRole(user.getUsername()));
            token = tokenService.generateToken(user);
        }

        if (token.isBlank()){
            throw new BadCredentialsException("Credentials are invalid(Please Check your password)");
        }

        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString()
            , "success"
            , null
            , null
        );

        ApiResponse<String> response = new ApiResponse<String>(
        "api.course.get",
        "v1",
        LocalDate.now().toString(), 
        params, 
        "OK", 
        token
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
