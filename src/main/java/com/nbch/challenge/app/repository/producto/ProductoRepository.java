package com.nbch.challenge.app.repository.producto;

import com.nbch.challenge.app.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> { }
