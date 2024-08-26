package com.nbch.challenge.app.exception;

import com.nbch.challenge.app.dtos.errors.ErrorGenerico;
import com.nbch.challenge.app.dtos.errors.ErrorNoEncontrado;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

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

        ErrorGenerico errorGenerico = new ErrorGenerico(ErrorConstants.ERROR_CREATION_ENTITY_TEMPLATE,
                StringUtils.join(errorList, ",")
                );

        return ResponseEntity.internalServerError().body(errorGenerico);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorGenerico> handleBindErrors(HandlerMethodValidationException exception){

        var errorList = exception.getAllValidationResults().stream()
                .map(
                        argumentError -> {
                            Map<String, Object > errorMap = new HashMap<>();
                            argumentError.getResolvableErrors().forEach(
                                    resolvableError -> {
                                        errorMap.put(resolvableError.getDefaultMessage(),argumentError.getArgument());
                                    }
                            );
                            return errorMap;
                        }
                ).toList();

        ErrorGenerico errorGenerico = new ErrorGenerico(ErrorConstants.ERROR_ARGUMENTS_TEMPLATE,
                StringUtils.join(errorList, ",")
        );

        return ResponseEntity.internalServerError().body(errorGenerico);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorNoEncontrado> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorNoEncontrado errorGenerico = new ErrorNoEncontrado(
                ErrorConstants.PRODUCTO_NO_EXISTE_TEMPLATE,
                exception.getMessage()
        );
        return new ResponseEntity<>(errorGenerico, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorGenerico> handleGlobalException(Exception exception, WebRequest webRequest){
        ErrorGenerico errorGenerico = new ErrorGenerico(
                ErrorConstants.ERROR_DESCONOCIDO_TEMPLATE,
                getMensajeErrorTemplate(webRequest,exception.getMessage())
        );
        return new ResponseEntity<>(errorGenerico, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getMensajeErrorTemplate(WebRequest webRequest, String mensaje){

        StringBuffer stringBuffer = new StringBuffer();

        return stringBuffer.append(webRequest.getDescription(false))
                .append(" ")
                .append("MENSAJE : ")
                .append(mensaje).toString();

    }
}
