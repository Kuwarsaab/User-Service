package com.saas.user_service.controller;

import com.saas.user_service.dtos.request.UserReqDto;
import com.saas.user_service.dtos.response.UserRespDto;
import com.saas.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.ServerException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService service;

    /** Only for testing purpose */
    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World!");
    }

    /*
     * This endpoint is used to create a new user. It accepts a UserReqDto object in
     * the request body and returns a UserRespDto object in the response body.
     * The endpoint is annotated with @PostMapping, which means it will handle HTTP
     * POST requests to the specified URL ("/register").
     */
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

    /*
     * This endpoint is used to retrieve a user by their email address. It accepts
     * the email address as a path variable and returns a UserRespDto object in the
     * response body.
     * The endpoint is annotated with @GetMapping, which means it will handle HTTP
     * GET
     * requests to the specified URL ("/get/{email}").
     */
    @GetMapping("/get/{email}")
    public ResponseEntity<UserRespDto> getUser(@Valid @PathVariable String email) throws NotFoundException {
        try {
            UserRespDto user = service.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            UserRespDto user = new UserRespDto();
            user.setErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(user);
        }
    }

    /*
     * This endpoint is used to retrieve all the users exist in the table. It returns
     * a List UserRespDto object in the response body.
     * The endpoint is annotated with @GetMapping, which means it will handle HTTP
     * GET
     * requests to the specified URL ("/get-all").
     */
    @GetMapping("/get-all")
    public List<UserRespDto> getAllUser() throws ServerException{
        try{
            List<UserRespDto> userResp = service.getAllUser();
            return userResp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
