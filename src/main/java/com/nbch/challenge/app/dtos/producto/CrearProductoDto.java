package com.nbch.challenge.app.dtos.producto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Schema(name = "Crear Producto",
        description = "Esquema para transferir la informacion del Producto nuevo"
)
public record CrearProductoDto(
        @Schema( description = "Nombre del producto", example = "Mate")
        @NotNull(message = "El nombre de producto no puede ser nulo")
        @NotBlank(message = "El nombre de producto no puede ser vacio")
        @Size(max = 100, message = "El nombre de producto no puede ser mayor a 100 caracteres")
        String nombre,

        @Schema( description = "Descripcion del producto", example = "Mate convencional", nullable = true )
        @Size(max = 5000, message = "La descripcion de producto no puede ser mayor a 5000 caracteres")
        String descripcion,

        @Schema( description = "Precio del producto", example = "200.00")
        @PositiveOrZero( message = "El precio debe ser 0 o un numero positivo")
        @NotNull(message = "El precio no puede ser nulo")
        double precio
){}
