package com.saas.user_service.repository;

import com.saas.user_service.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(@NotNull(message = "Email cannot be blank") @Email(message = "Invalid email format") String email);
}
