package ru.ibobrov.backend.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ibobrov.backend.exception.BadRequestException;
import ru.ibobrov.backend.exception.UserExistException;
import ru.ibobrov.backend.model.dto.ExceptionDto;

import static ru.ibobrov.backend.model.dto.ExceptionDto.StatusDto.*;

@Slf4j
@Component
public abstract class BaseController {
    public final static String CREDENTIAL_INCORRECT_MSG = "Неверные учетные данные пользователя";
    public final static String JWT_TOKEN_EXPIRED_MSG = "Срок действия jwt токена истек";
    public final static String JWT_TOKEN_INCORRECT_MSG = "Некорректный jwt токен";

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto> handleBadRequestException(Exception e) {
        final String message = e.getMessage();
        log.error(message);

        return new ResponseEntity<>(
                ExceptionDto.onError(message, BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleMethodArgumentNotValidException(Exception e) {
        final String message = e.getMessage();
        log.error(message);

        return new ResponseEntity<>(
                ExceptionDto.onError(message, BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionDto> handleBadCredentialsException(Exception e) {
        final String message = e.getMessage();
        log.error(message);

        return new ResponseEntity<>(
                ExceptionDto.onError(CREDENTIAL_INCORRECT_MSG, CREDENTIAL_INCORRECT),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ExceptionDto> handleSignatureException(Exception e) {
        final String message = e.getMessage();
        log.error(message);

        return new ResponseEntity<>(
                ExceptionDto.onError(JWT_TOKEN_INCORRECT_MSG, JWT_TOKEN_INCORRECT),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionDto> handleExpiredJwtException(Exception e) {
        final String message = e.getMessage();
        log.error(message);

        return new ResponseEntity<>(
                ExceptionDto.onError(JWT_TOKEN_EXPIRED_MSG, JWT_TOKEN_EXPIRED),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ExceptionDto> handleUserExistException(Exception e) {
        final String message = e.getMessage();
        log.error(message);

        return new ResponseEntity<>(
                ExceptionDto.onError(message, USER_EXIST),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ExceptionDto> handleDataAccessException(Exception e) {
        final String message = e.getMessage();
        log.error(message);

        return new ResponseEntity<>(
                ExceptionDto.onError(message, UNKNOWN_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleError(Exception e) {
        final String message = e.getMessage();
        log.error(message, e);

        return new ResponseEntity<>(
                ExceptionDto.onError(message, UNKNOWN_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
