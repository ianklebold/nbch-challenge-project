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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorGenerico> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorGenerico errorGenerico = new ErrorGenerico(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                getMensajeErrorTemplate(webRequest,exception.getMessage())
        );
        return new ResponseEntity<>(errorGenerico, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorGenerico> handleGlobalException(Exception exception, WebRequest webRequest){
        ErrorGenerico errorGenerico = new ErrorGenerico(
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                getMensajeErrorTemplate(webRequest,exception.getMessage())
        );
        return new ResponseEntity<>(errorGenerico, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getMensajeErrorTemplate(WebRequest webRequest, String mensaje){

        StringBuffer stringBuffer = new StringBuffer();

        return stringBuffer.append("URI : ")
                .append(webRequest.getDescription(false))
                .append(" ")
                .append("MENSAJE : ")
                .append(mensaje).toString();

    }
}
