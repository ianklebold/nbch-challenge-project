package com.nbch.challenge.app.dtos.errors;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorGenerico",
        description = "Esquema para transferir la informacion de ERROR en la Request"
)
public record ErrorGenerico(
        @Schema( description = "codigo de error", example = "ERROR_DESCONOCIDO") String codigo,
        @Schema( description = "mensaje de error", example = "Este es un mensaje de error", nullable = true) String mensaje) {
}
