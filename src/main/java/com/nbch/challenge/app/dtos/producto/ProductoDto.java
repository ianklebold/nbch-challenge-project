package com.nbch.challenge.app.dtos.producto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(name = "Producto",
        description = "Esquema para transferir la informacion de Producto"
)
@Builder
public record ProductoDto(
        @Schema( description = "Id del producto", example = "12") long id,
        @Schema( description = "Nombre del producto", example = "Mate") String nombre,
        @Schema( description = "Descripcion del producto", example = "Mate convencional", nullable = true ) String descripcion,
        @Schema( description = "Precio del producto", example = "200.00") double precio,
        @Schema( description = "Fecha de creacion del producto", example = "2022-04-07T01:35:22.413Z") LocalDateTime fechaCreacionProducto) {
        
}