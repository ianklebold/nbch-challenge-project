package com.nbch.challenge.app.controllers.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbch.challenge.app.controllers.ProductoController;
import com.nbch.challenge.app.dtos.producto.CrearProductoDto;
import com.nbch.challenge.app.dtos.producto.ProductoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProductControllerIT {

    @Autowired
    ProductoController productoController;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Nested
    @DisplayName("GET ENDPOINTS")
    class test_method_get_for_productos{
        @Test
        void test_get_all_products() throws Exception {
            mockMvc.perform(
                    get(ProductoController.PRODUCTO_PATH)
            ).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

    }

    @Nested
    @DisplayName("POST ENDPOINTS")
    class test_method_post_for_productos{

        @Test
        void test_create_a_correct_product() throws Exception {
            CrearProductoDto productoDto = new CrearProductoDto("nombre producto", "Descripcion producto",200);

            ResultActions result = mockMvc.perform(post(ProductoController.PRODUCTO_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(productoDto)));

            var resultado = result.andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

            var productoDtoCreated = objectMapper.readValue(resultado.getResponse().getContentAsString(), ProductoDto.class);

            assertEquals("nombre producto", productoDtoCreated.nombre());
            assertEquals("Descripcion producto", productoDtoCreated.descripcion());
            assertEquals(200.0, productoDtoCreated.precio());
            assertEquals(LocalDate.now(), productoDtoCreated.fechaCreacionProducto().toLocalDate());

        }

        @Test
        void test_get_correct_code_with_an_incorrect_name() throws Exception {
            CrearProductoDto productoDto = new CrearProductoDto("", "Descripcion producto",200);

            ResultActions result = mockMvc.perform(post(ProductoController.PRODUCTO_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(productoDto)));

            result.andExpect(status().isInternalServerError())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        }

        @Test
        void test_get_correct_code_with_an_incorrect_price() throws Exception {
            CrearProductoDto productoDto = new CrearProductoDto("", "Descripcion producto",-100);

            ResultActions result = mockMvc.perform(post(ProductoController.PRODUCTO_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(productoDto)));

            result.andExpect(status().isInternalServerError())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        }

    }


}
