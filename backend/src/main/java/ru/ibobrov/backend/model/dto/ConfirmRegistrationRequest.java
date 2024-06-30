package ru.ibobrov.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Schema(description = "Запрос подтверждения регистрации")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRegistrationRequest {

    @Schema(description = "токен", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG...")
    @NotNull
    private String token;

    @Schema(description = "пароль", example = "pass")
    @NotNull
    @Length(min = 8, max = 42)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[!@#$%^&*.,])(?=.*[a-z])(?=.*[A-Z])",
            message = "Должны быть заглавные буквы, прописные буквы, цифры, символы '!@#$%^&*.,'")
    private String password;
}
