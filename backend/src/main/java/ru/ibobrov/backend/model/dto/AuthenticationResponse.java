package ru.ibobrov.backend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Ответ на запрос авторизации")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    @Schema(description = "jwt токен", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG...")
    private String accessToken;

    @Schema(description = "активен n секунд", example = "86400")
    private Long expiration;
}
