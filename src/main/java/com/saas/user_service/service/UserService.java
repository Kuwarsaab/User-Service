package com.saas.user_service.service;

import com.saas.user_service.dtos.request.UserReqDto;
import com.saas.user_service.dtos.response.UserRespDto;
import com.saas.user_service.entity.User;
import com.saas.user_service.repository.UserRepository;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * This method is used to convert the User model to UserRespDto. It sets the id, name, email, and role of the user.
     */
    private UserRespDto convertModelToResponseDto(User user) {
        UserRespDto userRespDto = new UserRespDto();
        userRespDto.setId(user.getId());
        userRespDto.setName(user.getName());
        userRespDto.setEmail(user.getEmail());
        userRespDto.setRole(user.getRole());
        return userRespDto;
    }

    /*
     * This method is used to convert the UserReqDto to User model. It sets the email, name, password, and role of the user.
     * The password is encoded using the PasswordEncoder.
     */
    private User convertRequestDtoToModel(@Valid UserReqDto userReqDto) {
        User user = new User();
        user.setEmail(userReqDto.getEmail());
        user.setName(userReqDto.getName());
        user.setPassword(passwordEncoder.encode(userReqDto.getPassword()));
        user.setRole(userReqDto.getRole());
        return user;
    }

    /*
     * This method is used to create a new user. It checks if the user already exists by email.
     * If the user already exists, it throws a RuntimeException. Otherwise, it saves the user to the database.
     */
    public UserRespDto createUser(@Valid UserReqDto userReqDto) {
        if (repository.existsByEmail(userReqDto.getEmail())) {
            throw new RuntimeException();
        }
        User user = convertRequestDtoToModel(userReqDto);
        repository.save(user);
        return convertModelToResponseDto(user);
    }

    /**
     * This method is used to get the user by email.
     */
    public UserRespDto getUserByEmail(String email) {
        Optional<User> optUser = repository.findByEmail(email);

        if (optUser.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }

        User user = optUser.get();
        UserRespDto userRespDto = convertModelToResponseDto(user);
        return userRespDto;
    }

    public List<UserRespDto> getAllUser() {
        List<User> users = repository.findAll();
        List<UserRespDto> res = new ArrayList<>();
        for(User user : users){
            UserRespDto resp = convertModelToResponseDto(user);
            res.add(resp);
        }
        return res;
    }
}
