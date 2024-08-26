package com.nbch.challenge.app.service.producto;

import com.nbch.challenge.app.domain.Producto;
import com.nbch.challenge.app.dtos.producto.CrearProductoDto;
import com.nbch.challenge.app.dtos.producto.ProductoDto;
import com.nbch.challenge.app.exception.ResourceNotFoundException;
import com.nbch.challenge.app.mappers.producto.ProductoMapper;
import com.nbch.challenge.app.repository.producto.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nbch.challenge.app.controllers.ProductoController.PATH_ID_NAME;
import static com.nbch.challenge.app.controllers.ProductoController.RESOURCE_NAME;

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

    @Override
    public Optional<ProductoDto> getProductoById(long idProducto) {

        Optional<Producto> producto = productoRepository.findById(idProducto);

        if ( producto.isPresent() ){
            return Optional.of(productoMapper.productoToProductoDto(producto.get()));
        }

        throw new ResourceNotFoundException( Long.toString( idProducto ) );
    }

    @Override
    public boolean deleteProductoById(long idProducto) {

        Optional<Producto> producto = productoRepository.findById(idProducto);

        if ( producto.isPresent() ){
            productoRepository.delete(producto.get());
            return true;
        }

        throw new ResourceNotFoundException( Long.toString( idProducto ) );
    }


}
