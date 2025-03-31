package com.saas.user_service.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saas.user_service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRespDto {
    private String message;
    private String errorMessage;
    private Integer id;
    private String name;
    private String email;
    private Role role;
}
