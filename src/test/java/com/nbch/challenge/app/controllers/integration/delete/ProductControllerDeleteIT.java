package com.nbch.challenge.app.controllers.integration.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nbch.challenge.app.controllers.ProductoController;
import com.nbch.challenge.app.dtos.errors.ErrorNoEncontrado;
import com.nbch.challenge.app.dtos.producto.CrearProductoDto;
import com.nbch.challenge.app.dtos.producto.ProductoDto;
import com.nbch.challenge.app.exception.ErrorConstants;
import com.nbch.challenge.app.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.nbch.challenge.app.controllers.integration.post.ProductControllerPostIT.createProductTest;
import static com.nbch.challenge.app.controllers.integration.post.ProductControllerPostIT.getProductCreatedTest;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
public class ProductControllerDeleteIT {
    @Autowired
    ProductoController productoController;

    @Autowired
    WebApplicationContext wac;

    static MockMvc mockMvc;

    static ObjectMapper objectMapper;

    private final String PATH_DELETE_PRODUCT = "/api/v1/productos/{idProducto}";

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "producto");
    }

    @Nested
    @DisplayName("DELETE ENDPOINTS")
    class test_method_delete_for_productos{

        @WithMockUser
        @Test
        void test_resource_not_found_exception_when_i_want_to_delete_a_product_with_incorrect_id(){
            assertThrows(ResourceNotFoundException.class, () ->{
                productoController.deleteProductoById(123123123);
            });
        }

        @WithMockUser
        @Test
        void test_get_an_error_when_i_want_to_delete_a_product_with_incorrect_id() throws Exception {
            var resultado = getRequestResult(PATH_DELETE_PRODUCT,-1);

            var errorNoEncontradoDto = objectMapper.readValue(resultado.getResponse().getContentAsString(), ErrorNoEncontrado.class);

            assertEquals(ErrorConstants.ERROR_ARGUMENTS_TEMPLATE, errorNoEncontradoDto.codigo());
        }

        @WithMockUser
        @Rollback
        @Test
        void test_when_i_delete_a_product_the_count_of_total_product_is_minor_than_before() throws Exception {
            CrearProductoDto productoDto = new CrearProductoDto("nombre producto1", "Descripcion producto",200);
            CrearProductoDto productoDto2 = new CrearProductoDto("nombre producto2", "Descripcion producto2",600);

            var resultado = getProductCreatedTest(mockMvc,objectMapper.writeValueAsBytes(productoDto));

            createProductTest(mockMvc,objectMapper.writeValueAsBytes(productoDto2));

            var productoDtoCreated = objectMapper.readValue(resultado.getResponse().getContentAsString(), ProductoDto.class);

            testDeleteProduct(PATH_DELETE_PRODUCT, productoDtoCreated.id());

            mockMvc.perform(
                            get(ProductoController.PRODUCTO_PATH))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.length()",is(1)));



        }


    }

    public static MvcResult getRequestResult(String path, long uriVariable) throws Exception {
        return mockMvc.perform(delete(path, uriVariable)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }


    public static void testDeleteProduct(String path, long idProduct) throws Exception {
        mockMvc.perform(delete(path, idProduct)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
