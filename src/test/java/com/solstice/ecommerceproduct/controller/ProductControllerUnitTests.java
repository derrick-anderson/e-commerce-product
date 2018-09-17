package com.solstice.ecommerceproduct.controller;

import com.solstice.ecommerceproduct.data.ProductRepository;
import com.solstice.ecommerceproduct.domain.Product;
import com.solstice.ecommerceproduct.service.ProductServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(secure = false)
public class ProductControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServices productServices;

    private ProductController productController;

    @Before
    public void setup(){
        productController = new ProductController(productServices);
    }

    @Test
    public void getAllProducts_HappyPath() throws Exception {
        when(productServices.getAllProducts()).thenReturn(getMockProductList());

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].productId", is(11)))
                .andExpect(jsonPath("$[1].productId", is(12)))
                .andExpect(jsonPath("$[2].productId", is(13)))
                .andExpect(jsonPath("$[3].productId", is(14)))
                .andExpect(jsonPath("$[4].productId", is(15)));
    }

    @Test
    public void getOneProduct_HappyPath() throws Exception{
        when(productServices.getOneProduct(15L)).thenReturn(getMockProduct(15L));

        mockMvc.perform(get("/products/15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.productId", is(15)))
                .andExpect(jsonPath("$.name", is("Teddy Bonkers")))
                .andExpect(jsonPath("$.description", is("Theodore Bonkers: The Junior Detective")))
                .andExpect(jsonPath("$.imageSrc", is("tbonkers.com/home.jpg")))
                .andExpect(jsonPath("$.price", is(180.00)));
    }

    @Test
    public void deleteProduct_HappyPath() throws Exception{
        mockMvc.perform(delete("/products/15"))
                .andExpect(status().isNoContent());
        verify(productServices, times(1)).deleteProduct(15L);

    }

    @Test
    public void saveProduct_HappyPath() throws Exception{
        when(productServices.saveProduct(any(Product.class))).thenReturn(getMockProduct(15L));

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.productId", is(15)))
                .andExpect(jsonPath("$.name", is("Teddy Bonkers")))
                .andExpect(jsonPath("$.description", is("Theodore Bonkers: The Junior Detective")))
                .andExpect(jsonPath("$.imageSrc", is("tbonkers.com/home.jpg")))
                .andExpect(jsonPath("$.price", is(180.00)));
    }

    @Test
    public void updateProduct_HappyPath() throws Exception{
        when(productServices.updateProduct(eq(15L), any(Product.class))).thenReturn(getMockProduct(15L));

        mockMvc.perform(put("/products/15")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\": \"Theodore Bonkers\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.productId", is(15)))
                .andExpect(jsonPath("$.name", is("Teddy Bonkers")))
                .andExpect(jsonPath("$.description", is("Theodore Bonkers: The Junior Detective")))
                .andExpect(jsonPath("$.imageSrc", is("tbonkers.com/home.jpg")))
                .andExpect(jsonPath("$.price", is(180.00)));
    }

    private String productJson = "{ \"name\": \"Teddy Bonkers\", \"description\": \"Theodore Bonkers: The Junior Detective\", \"imageSrc\": \"tbonkers.com/home.jpg\", \"price\": 180.00}";

    private Product getMockProduct(Long productId) {
        Product mockProduct =  new Product("Teddy Bonkers", "Theodore Bonkers: The Junior Detective", "tbonkers.com/home.jpg", new BigDecimal("180.00"));
        mockProduct.setProductId(productId);
        return mockProduct;
    }

    private List<Product> getMockProductList() {
        return new ArrayList<Product>(){{
            add(getMockProduct(11L));
            add(getMockProduct(12L));
            add(getMockProduct(13L));
            add(getMockProduct(14L));
            add(getMockProduct(15L));
        }};
    }
}
