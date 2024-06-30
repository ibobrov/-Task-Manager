package ru.ibobrov.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Запрос авторизации")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @Schema(description = "email", example = "user@domen.ru")
    @NotNull
    @Email
    private String email;

    @Schema(description = "пароль", example = "pass")
    @NotNull
    private String password;
}