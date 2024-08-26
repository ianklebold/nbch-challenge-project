package com.nbch.challenge.app.controllers.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbch.challenge.app.controllers.ProductoController;
import com.nbch.challenge.app.dtos.errors.ErrorGenerico;
import com.nbch.challenge.app.dtos.errors.ErrorNoEncontrado;
import com.nbch.challenge.app.dtos.producto.CrearProductoDto;
import com.nbch.challenge.app.dtos.producto.ProductoDto;
import com.nbch.challenge.app.exception.ErrorConstants;
import com.nbch.challenge.app.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.core.Is.is;

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

    private final String NAME_WITH_100_CHARACTERS = "asdasdasdasdasd asdasdasd asdasdasdas asdasdasdw3qdads asdwqdasdwdasdsda asdasdasdas asdasdsadas sda";

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Nested
    @DisplayName("GET ENDPOINTS")
    class test_method_get_for_productos{
        @Test
        void test_get_all_products_without_elements() throws Exception {
            mockMvc.perform(
                    get(ProductoController.PRODUCTO_PATH)
            ).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()",is(0)));
        }

        @Test
        @Rollback
        void test_get_all_products_with_elements() throws Exception {
            CrearProductoDto productoDto = new CrearProductoDto("nombre producto1", "Descripcion producto",200);
            CrearProductoDto productoDto2 = new CrearProductoDto("nombre producto2", "Descripcion producto2",600);

             mockMvc.perform(post(ProductoController.PRODUCTO_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(productoDto)));

            mockMvc.perform(post(ProductoController.PRODUCTO_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(productoDto2)));

            mockMvc.perform(
                    get(ProductoController.PRODUCTO_PATH))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(jsonPath("$.length()",is(2)));
        }

        @Test
        void test_resource_not_found_exception_when_i_want_to_find_a_product_with_incorrect_id(){
            assertThrows(ResourceNotFoundException.class, () ->{
                productoController.getProductoById(123123123);
            });
        }

        @Test
        void test_get_an_error_when_i_want_to_find_a_product_with_incorrect_id() throws Exception {
            var resultado = mockMvc.perform(get("/api/v1/productos/{idProducto}", -1)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            var errorNoEncontradoDto = objectMapper.readValue(resultado.getResponse().getContentAsString(), ErrorNoEncontrado.class);

            assertEquals(ErrorConstants.ERROR_ARGUMENTS_TEMPLATE, errorNoEncontradoDto.codigo());
        }

        @Test
        void test_get_an_correct_error_when_i_want_to_find_a_product_with_incorrect_id() throws Exception {
            var resultado = mockMvc.perform(get("/api/v1/productos/{idProducto}", 123123123)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            var errorNoEncontradoDto = objectMapper.readValue(resultado.getResponse().getContentAsString(), ErrorNoEncontrado.class);

            assertEquals(ErrorConstants.PRODUCTO_NO_EXISTE_TEMPLATE, errorNoEncontradoDto.codigo());
            assertNotEquals(null, errorNoEncontradoDto.mensaje());
        }

        @Rollback
        @Test
        void test_get_product_when_i_want_to_find_a_product_with_correct_id() throws Exception {
            CrearProductoDto productoDto = new CrearProductoDto("nombre producto by id", "Descripcion producto diferente",1000);
            ResultActions result = mockMvc.perform(post(ProductoController.PRODUCTO_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(productoDto)));

            var resultado = result.andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

            var productoDtoCreated = objectMapper.readValue(resultado.getResponse().getContentAsString(), ProductoDto.class);

            var resultadoBusqueda = mockMvc.perform(get("/api/v1/productos/{idProducto}", productoDtoCreated.id())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            var productoDtoSearched = objectMapper.readValue(resultadoBusqueda.getResponse().getContentAsString(), ProductoDto.class);

            assertEquals("nombre producto by id", productoDtoSearched.nombre());
            assertEquals("Descripcion producto diferente", productoDtoSearched.descripcion());
            assertEquals(1000.0, productoDtoSearched.precio());
            assertEquals(LocalDate.now(), productoDtoSearched.fechaCreacionProducto().toLocalDate());
        }

    }

    @Nested
    @DisplayName("POST ENDPOINTS")
    class test_method_post_for_productos{

        @Test
        void test_create_a_correct_product() throws Exception {
            CrearProductoDto productoDto = new CrearProductoDto(NAME_WITH_100_CHARACTERS, "Descripcion producto",200);

            ResultActions result = mockMvc.perform(post(ProductoController.PRODUCTO_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(productoDto)));

            var resultado = result.andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

            var productoDtoCreated = objectMapper.readValue(resultado.getResponse().getContentAsString(), ProductoDto.class);

            assertEquals(NAME_WITH_100_CHARACTERS, productoDtoCreated.nombre());
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

            var resultado = result.andExpect(status().isInternalServerError())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            var genericErrorDto = objectMapper.readValue(resultado.getResponse().getContentAsString(), ErrorGenerico.class);

            assertEquals(ErrorConstants.ERROR_CREATION_ENTITY_TEMPLATE, genericErrorDto.codigo());
            assertNotEquals(null, genericErrorDto.mensaje());

        }

        @Test
        void test_get_correct_code_with_an_incorrect_price() throws Exception {
            CrearProductoDto productoDto = new CrearProductoDto("", "Descripcion producto",-100);

            ResultActions result = mockMvc.perform(post(ProductoController.PRODUCTO_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(productoDto)));

            var resultado = result.andExpect(status().isInternalServerError())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            var genericErrorDto = objectMapper.readValue(resultado.getResponse().getContentAsString(), ErrorGenerico.class);

            assertEquals(ErrorConstants.ERROR_CREATION_ENTITY_TEMPLATE, genericErrorDto.codigo());
            assertNotEquals(null, genericErrorDto.mensaje());
        }

    }


}
