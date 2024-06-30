package ru.ibobrov.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.ibobrov.backend.security.UserDetailsImpl;

@Schema(description = "Запрос создания уз пользователя")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest{

    @Schema(description = "ФИО", example = "Иванов Иван Иванович")
    @NotNull
    @Length(min = 2)
    private String fullName;

    @Schema(description = "email", example = "user@domen.ru")
    @NotNull
    @Email(regexp = "([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)")
    private String email;

    @Schema(description = "Роль уз", example = "SPECIALIST")
    @NotNull
    private UserDetailsImpl.Role role;
}
