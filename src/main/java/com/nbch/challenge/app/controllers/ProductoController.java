package com.nbch.challenge.app.controllers;

import com.nbch.challenge.app.domain.Producto;
import com.nbch.challenge.app.dtos.errors.ErrorGenerico;
import com.nbch.challenge.app.dtos.errors.ErrorNoEncontrado;
import com.nbch.challenge.app.dtos.producto.CrearProductoDto;
import com.nbch.challenge.app.dtos.producto.ProductoDto;
import com.nbch.challenge.app.exception.ResourceNotFoundException;
import com.nbch.challenge.app.service.producto.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Optional;

import static com.nbch.challenge.app.controllers.ProductoController.PRODUCTO_PATH;

@Tag(
        name = "API REST de Productos",
        description = "API REST DEL PROYECTO PARA CREAR, ACTUALIZAR, OBTENER Y ELIMINAR PRODUCTOS"
)
@AllArgsConstructor
@RestController
@RequestMapping(path = PRODUCTO_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
public class ProductoController {

    public static final String RESOURCE_NAME = "producto";

    public static final String PATH_ID_NAME = "idProducto";

    public static final String PRODUCTO_PATH = "/api/v1/productos";

    public static final String PATH_ID = "/{"+PATH_ID_NAME+"}";

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
    public ResponseEntity<?> createProduct(@Valid @RequestBody CrearProductoDto crearProductoDto){

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

    @Operation(
            summary = "API REST para obtener todos los productos",
            description = "API REST que permite obtener todos los productos"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductoDto.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status SERVER ERROR",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorGenerico.class)
                    )
            )
    })
    @GetMapping
    public List<ProductoDto> getProductos(){

        return productoService.getProductos();

    }

    @Operation(
            summary = "API REST para obtener producto por id",
            description = "API REST que permite obtener producto por id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = ProductoDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status NOT FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorNoEncontrado.class)
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
    @GetMapping(PATH_ID)
    public ResponseEntity<ProductoDto> getProductoById( @PathVariable( PATH_ID_NAME )
                                                           @NotNull( message = "El id no debe ser nulo" )
                                                           @Positive( message = "El id debe ser positivo" ) long idProducto ){

        Optional<ProductoDto> producto = productoService.getProductoById( idProducto );

        if ( producto.isPresent() ){

            var productoDto = producto.get();

            return ResponseEntity
                    .status( HttpStatus.OK )
                    .body( productoDto );
        }

        throw new ResourceNotFoundException( Long.toString( idProducto ) );

    }

    @Operation(
            summary = "API REST para eliminar un producto por id",
            description = "API REST que permite eliminar producto por id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "HTTP Status NOT CONTENT"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status NOT FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorNoEncontrado.class)
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
    @DeleteMapping(PATH_ID)
    public ResponseEntity<?> deleteProductoById( @PathVariable( PATH_ID_NAME )
                                                            @NotNull( message = "El id no debe ser nulo" )
                                                            @Positive( message = "El id debe ser positivo" ) long idProducto ){
        productoService.deleteProductoById( idProducto );
        return ResponseEntity.status( HttpStatus.NO_CONTENT ).build();
    }


}
