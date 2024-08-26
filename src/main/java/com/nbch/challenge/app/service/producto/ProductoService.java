package com.nbch.challenge.app.service.producto;

import com.nbch.challenge.app.domain.Producto;
import com.nbch.challenge.app.dtos.producto.CrearProductoDto;
import com.nbch.challenge.app.dtos.producto.ProductoDto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    Producto createProducto(CrearProductoDto crearProductoDto);

    List<ProductoDto> getProductos();

    Optional<ProductoDto> getProductoById(long idProducto);
}
