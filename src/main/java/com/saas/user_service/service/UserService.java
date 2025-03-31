package com.saas.user_service.service;

import com.saas.user_service.dtos.request.UserReqDto;
import com.saas.user_service.dtos.response.UserRespDto;
import com.saas.user_service.entity.User;
import com.saas.user_service.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private UserRespDto convertModelToResponseDto(User user) {
        UserRespDto userRespDto = new UserRespDto();
        userRespDto.setId(user.getId());
        userRespDto.setName(user.getName());
        userRespDto.setEmail(user.getEmail());
        userRespDto.setRole(user.getRole());
        return userRespDto;
    }

    private User convertRequestDtoToModel(@Valid UserReqDto userReqDto) {
        User user = new User();
        user.setEmail(userReqDto.getEmail());
        user.setName(userReqDto.getName());
        user.setPassword(passwordEncoder.encode(userReqDto.getPassword()));
        user.setRole(userReqDto.getRole());
        return user;
    }

    public UserRespDto createUser(@Valid UserReqDto userReqDto) {
        if (repository.existsByEmail(userReqDto.getEmail())) {
            throw new RuntimeException();
        }
        User user = convertRequestDtoToModel(userReqDto);
        repository.save(user);
        return convertModelToResponseDto(user);
    }




}
