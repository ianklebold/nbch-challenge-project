package com.nbch.challenge.app.service.producto;

import com.nbch.challenge.app.domain.Producto;
import com.nbch.challenge.app.dtos.producto.CrearProductoDto;

public interface ProductoService {

    Producto createProducto(CrearProductoDto crearProductoDto);

}
