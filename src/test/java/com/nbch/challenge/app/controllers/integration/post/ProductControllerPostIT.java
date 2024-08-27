package com.nbch.challenge.app.controllers.integration.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nbch.challenge.app.controllers.ProductoController;
import com.nbch.challenge.app.dtos.errors.ErrorGenerico;
import com.nbch.challenge.app.dtos.producto.CrearProductoDto;
import com.nbch.challenge.app.dtos.producto.ProductoDto;
import com.nbch.challenge.app.exception.ErrorConstants;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class ProductControllerPostIT {

    @Autowired
    ProductoController productoController;

    @Autowired
    WebApplicationContext wac;

    static MockMvc mockMvc;

    static ObjectMapper objectMapper;

    private final String NAME_WITH_100_CHARACTERS = "asdasdasdasdasd asdasdasd asdasdasdas asdasdasdw3qdads asdwqdasdwdasdsda asdasdasdas asdasdsadas sda";


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
    @DisplayName("POST ENDPOINTS")
    class test_method_post_for_productos{

        @Test
        @Rollback
        void test_create_a_correct_product() throws Exception {
            CrearProductoDto productoDto = new CrearProductoDto(NAME_WITH_100_CHARACTERS, "Descripcion producto",200);

            ResultActions result = createProductTest(mockMvc,objectMapper.writeValueAsBytes(productoDto));

            var resultado = result.andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

            var productoDtoCreated = objectMapper.readValue(resultado.getResponse().getContentAsString(), ProductoDto.class);

            assertEquals(NAME_WITH_100_CHARACTERS, productoDtoCreated.nombre());
            assertEquals("Descripcion producto", productoDtoCreated.descripcion());
            assertEquals(200.0, productoDtoCreated.precio());
            assertEquals(LocalDate.now(), productoDtoCreated.fechaCreacionProducto().toLocalDate());

        }

        @Test
        @Rollback
        void test_get_correct_code_with_an_incorrect_name() throws Exception {
            CrearProductoDto productoDto = new CrearProductoDto("", "Descripcion producto",200);

            ResultActions result = createProductTest(mockMvc,objectMapper.writeValueAsBytes(productoDto));

            var resultado = result.andExpect(status().isInternalServerError())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            var genericErrorDto = objectMapper.readValue(resultado.getResponse().getContentAsString(), ErrorGenerico.class);

            assertEquals(ErrorConstants.ERROR_CREATION_ENTITY_TEMPLATE, genericErrorDto.codigo());
            assertNotEquals(null, genericErrorDto.mensaje());

        }



        @Test
        @Rollback
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

    public static ResultActions createProductTest(MockMvc mockMvc, byte[] productoDto) throws Exception {
        return mockMvc.perform(post(ProductoController.PRODUCTO_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(productoDto));
    }

    public static MvcResult getProductCreatedTest(MockMvc mockMvc, byte[] productoDto) throws Exception {
        return mockMvc.perform(post(ProductoController.PRODUCTO_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productoDto))
                .andReturn();
    }

}
