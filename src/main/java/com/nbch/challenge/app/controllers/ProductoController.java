package com.nbch.challenge.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.nbch.challenge.app.controllers.ProductoController.PRODUCTO_PATH;

@RestController
@RequestMapping(PRODUCTO_PATH)
public class ProductoController {

    public static final String PRODUCTO_PATH = "/api/v1/productos";

    @GetMapping
    public void getProducto(){
        System.out.println("Todo bien por aca!");
    }
}
