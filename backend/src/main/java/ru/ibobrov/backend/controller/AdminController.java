package ru.ibobrov.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ibobrov.backend.model.dto.ExceptionDto;
import ru.ibobrov.backend.model.dto.NewUserRequest;
import ru.ibobrov.backend.model.dto.UpdateUserRequest;
import ru.ibobrov.backend.security.UserDetailsImpl;
import ru.ibobrov.backend.service.UserService;

@RestController
@AllArgsConstructor
public class AdminController extends BaseController {
    private final UserService userService;

    @PostMapping("/api/admin/create-user")
    @Operation(summary = "Создать уз пользователя", description = "Уз пользователя создаётся с пустым паролем и требуется подтверждение регистрации."
            + "На почту высылается письмо через внешний почтовый сервер. В письме ссылка с вложенным jwt токеном.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Уз пользователя создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные", content = {
                    @Content(schema = @Schema(implementation = ExceptionDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Требуется авторизация с ролью ADMIN"),
            @ApiResponse(responseCode = "409", description = "Email используется", content = {
                    @Content(schema = @Schema(implementation = ExceptionDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка", content = {
                    @Content(schema = @Schema(implementation = ExceptionDto.class)) })
    })
    public ResponseEntity<Void> createUser(@RequestBody @Validated NewUserRequest newUserRequest) {

        userService.create(newUserRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/api/admin/update-user/{id}")
    @Operation(summary = "Редактировать уз пользователя", description = "Метод работает в бесшумном режиме, если уз есть в системе "
            + "её данные обновляются, если нет никаких уведомлений нет.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Данные в уз пользователя обновлены"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные", content = {
                    @Content(schema = @Schema(implementation = ExceptionDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Требуется авторизация с ролью ADMIN"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка", content = {
                    @Content(schema = @Schema(implementation = ExceptionDto.class)) })
    })
    public void updateUser(@PathVariable Long id, @RequestBody @Validated UpdateUserRequest updateUserRequest) {

        userService.update(id, updateUserRequest);
    }

    @PostMapping("/api/admin/enable-user/{id}")
    @Operation(summary = "Включить уз пользователя", description = "Метод работает в бесшумном режиме, если уз есть в системе "
            + "ей выставляется активный статус, если нет никаких уведомлений нет.")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Уз пользователя активирована"),
            @ApiResponse(responseCode = "403", description = "Требуется авторизация с ролью ADMIN"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка", content = {
                    @Content(schema = @Schema(implementation = ExceptionDto.class)) })
    })
    public void enableUser(@PathVariable Long id) {

        userService.setActiveStatus(id, true);
    }

    @GetMapping("/api/admin/available-roles")
    @Operation(summary = "Актуальные роли пользователей в системе")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ОК"),
            @ApiResponse(responseCode = "403", description = "Требуется авторизация с ролью ADMIN"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка", content = {
                    @Content(schema = @Schema(implementation = ExceptionDto.class)) })
    })
    public UserDetailsImpl.Role[] getAvailableRoles() {

        return userService.getAvailableRoles();
    }
}
