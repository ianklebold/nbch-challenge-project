package com.nbch.challenge.app.dtos.producto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Producto",
        description = "Esquema para transferir la informacion del Producto nuevo"
)
public record CrearProductoDto(
        @Schema( description = "Nombre del producto", example = "Mate") String nombre,
        @Schema( description = "Descripcion del producto", example = "Mate convencional", nullable = true ) String descripcion,
        @Schema( description = "Precio del producto", example = "200.00") double precio){

}
