package com.nbch.challenge.app.service.producto;

import com.nbch.challenge.app.domain.Producto;
import com.nbch.challenge.app.dtos.producto.CrearProductoDto;
import com.nbch.challenge.app.dtos.producto.ProductoDto;
import com.nbch.challenge.app.mappers.producto.ProductoMapper;
import com.nbch.challenge.app.repository.producto.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoServiceImpl implements ProductoService{

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    @Override
    public Producto createProducto(CrearProductoDto crearProductoDto) {

        Producto producto = productoMapper.crearProductoDtoToProducto(crearProductoDto);

        producto = productoRepository.save(producto);

        return producto;
    }

    @Override
    public List<ProductoDto> getProductos() {

        return productoRepository.findAll()
                .stream()
                .map(productoMapper::productoToProductoDto)
                .toList();

    }


}
