package com.nbch.challenge.app.exception;

import com.nbch.challenge.app.dtos.ErrorGenerico;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorGenerico> handleBindErrors(MethodArgumentNotValidException exception){

        var errorList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String > errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();

        ErrorGenerico errorGenerico = new ErrorGenerico("Error en la creacion de la entidad",
                StringUtils.join(errorList, ",")
                );

        return ResponseEntity.internalServerError().body(errorGenerico);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorGenerico> handleGlobalException(Exception exception, WebRequest webRequest){
        ErrorGenerico errorGenerico = new ErrorGenerico(exception.getMessage(),webRequest.getDescription(false));

        return new ResponseEntity<>(errorGenerico, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
