package com.saas.user_service.controller;

import com.saas.user_service.dtos.request.UserReqDto;
import com.saas.user_service.dtos.response.UserRespDto;
import com.saas.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.ServerException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserRespDto> createUser(@Valid @RequestBody UserReqDto userReqDto) throws ServerException {
        try {
            // create the user
            UserRespDto userRespDto = service.createUser(userReqDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userRespDto);

        } catch (Exception e) {
            // Handle server-side errors
            throw new ServerException("Server Error: " + e.getMessage());
        }
    }

}
