package ru.ibobrov.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ibobrov.backend.model.dto.AuthenticationRequest;
import ru.ibobrov.backend.model.dto.AuthenticationResponse;
import ru.ibobrov.backend.model.dto.ConfirmRegistrationRequest;
import ru.ibobrov.backend.model.dto.ExceptionDto;
import ru.ibobrov.backend.security.AuthenticationService;
import ru.ibobrov.backend.service.UserService;

@Slf4j
@RestController
@AllArgsConstructor
public class PublicController extends BaseController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/api/public/auth")
    @Operation(summary = "Сгенерировать jwt токен авторизации")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен сгенерирован"),
            @ApiResponse(responseCode = "400", description = "Неверные учетные данные пользователя", content = {
                    @Content(schema = @Schema(implementation = ExceptionDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка", content = {
                    @Content(schema = @Schema(implementation = ExceptionDto.class)) })
    })
    public AuthenticationResponse authenticate(@RequestBody @Validated AuthenticationRequest request) {

        return authenticationService.authenticate(request);
    }

    @PostMapping("/api/public/confirm-registration")
    @Operation(summary = "Подтвердить регистрацию через токен и задать пароль")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен сгенерирован"),
            @ApiResponse(responseCode = "400", description = "Уз пользователя неактивна, токен невалиден/истек", content = {
                    @Content(schema = @Schema(implementation = ExceptionDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка", content = {
                    @Content(schema = @Schema(implementation = ExceptionDto.class)) })
    })
    public void confirmRegistration(@RequestBody @Validated ConfirmRegistrationRequest request) {

        userService.confirmRegistration(request);
    }
}
