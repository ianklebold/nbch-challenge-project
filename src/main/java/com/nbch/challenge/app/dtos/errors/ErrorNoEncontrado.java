package com.nbch.challenge.app.dtos.errors;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorNoEncontrado",
        description = "Esquema para transferir la informacion de ERROR cuando no se encuentra un recurso"
)
public record ErrorNoEncontrado(
        @Schema( description = "codigo de error", example = "PRODUCTO_NO_EXISTE") String codigo,
        @Schema( description = "mensaje de error", example = "El producto(id=12) no existe", nullable = true) String mensaje) {
}
