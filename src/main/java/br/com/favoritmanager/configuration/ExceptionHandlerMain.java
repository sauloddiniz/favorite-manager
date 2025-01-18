package br.com.favoritmanager.configuration;

import br.com.favoritmanager.core.model.exception.EmailAlreadyRegisterException;
import br.com.favoritmanager.core.model.exception.ClientNotFoundException;
import br.com.favoritmanager.core.model.exception.ProductAlreadyExistException;
import br.com.favoritmanager.core.model.exception.ValueIsEmptyOrBlankException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerMain {

    @ExceptionHandler(EmailAlreadyRegisterException.class)
    public ResponseEntity<Object> emailAlreadyRegisterException(EmailAlreadyRegisterException ex, HttpServletRequest request) {
        Map<String, Object> body = extractErrorInfo(ex, request);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValueIsEmptyOrBlankException.class)
    public ResponseEntity<Object> valueIsEmptyOrBlankException(ValueIsEmptyOrBlankException ex, HttpServletRequest request) {
        Map<String, Object> body = extractErrorInfo(ex, request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Object> emptyResultDataAccessException(ClientNotFoundException ex, HttpServletRequest request) {
        Map<String, Object> body = extractErrorInfo(ex, request);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<Object> productAlreadyExistException(ProductAlreadyExistException ex, HttpServletRequest request) {
        Map<String, Object> body = extractErrorInfo(ex, request);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    private static Map<String, Object> extractErrorInfo(Exception ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("date-hour", LocalDateTime.now());
        body.put("error", ex.getMessage());
        body.put("path", request.getRequestURI());
        return body;
    }

}
