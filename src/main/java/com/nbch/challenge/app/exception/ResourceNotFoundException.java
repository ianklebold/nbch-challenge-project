package com.nbch.challenge.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String nombreRecurso, String nombreCampo, String valorCampo){
        super(String.format("%s no se encuentra con el dato %s : '%s'", nombreRecurso, nombreCampo, valorCampo));
    }

}
