package com.nbch.challenge.app.controllers;

import com.nbch.challenge.app.domain.Producto;
import com.nbch.challenge.app.dtos.ErrorGenerico;
import com.nbch.challenge.app.dtos.producto.CrearProductoDto;
import com.nbch.challenge.app.dtos.producto.ProductoDto;
import com.nbch.challenge.app.service.producto.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.nbch.challenge.app.controllers.ProductoController.PRODUCTO_PATH;

@Tag(
        name = "API REST de Productos",
        description = "API REST DEL PROYECTO PARA CREAR, ACTUALIZAR, OBTENER Y ELIMINAR PRODUCTOS"
)
@AllArgsConstructor
@RestController
@RequestMapping(path = PRODUCTO_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
public class ProductoController {

    public static final String PRODUCTO_PATH = "/api/v1/productos";

    private final ProductoService productoService;

    @Operation(
            summary = "API REST para Crear Producto",
            description = " API REST que permite crear un producto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED",
                    content = @Content(
                            schema = @Schema(implementation = ProductoDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status SERVER ERROR",
                    content = @Content(
                            schema = @Schema(implementation = ErrorGenerico.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody CrearProductoDto crearProductoDto){

        Producto producto = productoService.createProducto(crearProductoDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ProductoDto(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getPrecio(),
                        producto.getFechaCreacionProducto()
                ));
    }
}
