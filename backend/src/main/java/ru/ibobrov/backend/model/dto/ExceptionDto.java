package ru.ibobrov.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "Детализированный ответ ошибки")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {

    @Schema(description = "Текст ошибки", example = "Сообщение об ошибке")
    private String message;

    @Schema(description = "Внутренний код ошибки", example = "-99")
    private StatusDto status;

    public static ExceptionDto onError(String msg, StatusDto statusDto) {
        return new ExceptionDto(msg, statusDto);
    }

    @Getter
    @AllArgsConstructor
    public enum StatusDto {
        OK(0),
        AUTH_REQUIRED(-10),
        CREDENTIAL_INCORRECT(-11),
        JWT_TOKEN_EXPIRED(-12),
        JWT_TOKEN_INCORRECT(-13),
        USER_EXIST(-14),
        BAD_REQUEST(-15),
        UNKNOWN_ERROR(-99);

        @JsonValue
        private final int code;
    }
}
