package com.nbch.challenge.app.mappers.producto;

import com.nbch.challenge.app.domain.Producto;
import com.nbch.challenge.app.dtos.producto.CrearProductoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductoMapper {

    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "precio", target = "precio")
    Producto crearProductoDtoToProducto(CrearProductoDto crearProductoDto);


}
