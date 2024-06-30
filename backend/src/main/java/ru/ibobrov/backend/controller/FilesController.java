package ru.ibobrov.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.ibobrov.backend.model.dto.ExceptionDto;
import ru.ibobrov.backend.security.UserDetailsImpl;
import ru.ibobrov.backend.service.FileService;

@Controller
@AllArgsConstructor
public class FilesController extends BaseController {
    private final FileService fileService;

    @PostMapping(path = "/api/public/photos/upload-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Обновить фото пользователя",
            description = "Можно загрузить фото только в формате jpeg/jpg")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Фото сохранено в системе"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные", content = {
                    @Content(schema = @Schema(implementation = ExceptionDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Требуется авторизация с любой ролью"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка", content = {
                    @Content(schema = @Schema(implementation = ExceptionDto.class)) })
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> uploadPhoto(@RequestPart MultipartFile photo) {

        final UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        fileService.saveUserPhoto(photo, userDetails.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
