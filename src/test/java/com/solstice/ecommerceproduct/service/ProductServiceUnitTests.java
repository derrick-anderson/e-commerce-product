package com.solstice.ecommerceproduct.service;

import com.solstice.ecommerceproduct.data.ProductRepository;
import com.solstice.ecommerceproduct.domain.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class ProductServiceUnitTests {

    @MockBean
    private ProductRepository productRepository;

    private ProductServices productServices;

    @Before
    public void setup(){
        productServices = new ProductServices(productRepository);
    }

    @Test
    public void saveProduct_HappyPath(){
        when(productRepository.save(any(Product.class))).thenReturn(getMockProduct(15L));

        Product savedProduct = productServices.saveProduct(getMockProduct(15L));

        assertThat(savedProduct.getProductId(), is(15L));
        assertThat(savedProduct.getName(), is("Teddy Bonkers"));
        assertThat(savedProduct.getDescription(), is("Theodore Bonkers: The Junion Detective"));
        assertThat(savedProduct.getImageSrc(), is("tbonkers.com/home.jpg"));
        assertThat(savedProduct.getPrice(), is(new BigDecimal("180.00")));
    }

    @Test
    public void getOneProduct_HappyPath(){
        when(productRepository.getOne(anyLong())).thenReturn(getMockProduct(15L));

        Product foundProduct = productServices.getOneProduct(15L);

        assertThat(foundProduct.getProductId(), is(15L));
        assertThat(foundProduct.getName(), is("Teddy Bonkers"));
        assertThat(foundProduct.getDescription(), is("Theodore Bonkers: The Junion Detective"));
        assertThat(foundProduct.getImageSrc(), is("tbonkers.com/home.jpg"));
        assertThat(foundProduct.getPrice(), is(new BigDecimal("180.00")));
    }

    @Test
    public void getAllProducts_HappyPath(){
        when(productRepository.findAll()).thenReturn(getMockProductList());

        List<Product> foundProducts = productServices.getAllProducts();
        assertThat(foundProducts.size(), is(5));
        assertThat(foundProducts.get(0).getProductId(), is(11L));
        assertThat(foundProducts.get(1).getName(), is("Teddy Bonkers"));
        assertThat(foundProducts.get(2).getDescription(), is("Theodore Bonkers: The Junion Detective"));
        assertThat(foundProducts.get(3).getImageSrc(), is("tbonkers.com/home.jpg"));
        assertThat(foundProducts.get(4).getPrice(), is(new BigDecimal("180.00")));
    }

    @Test
    public void deleteProduct_HappyPath(){
        when(productRepository.getOne(15L)).thenReturn(getMockProduct(15L));
        productServices.deleteProduct(15L);

        verify(productRepository, times(1)).deleteById(15L);
    }

    @Test
    public void updateProduct_HappyPath(){
        when(productRepository.getOne(15L)).thenReturn(getMockProduct(15L));
        Product productUpdateData = new Product("Theodore Bonkers", null, null, BigDecimal.ZERO);
        Product updatedProduct = productServices.updateProduct(15L, productUpdateData);

        assertThat(updatedProduct.getProductId(), is(15L));
        assertThat(updatedProduct.getName(), is("Theodore Bonkers"));
        assertThat(updatedProduct.getDescription(), is("Theodore Bonkers: The Junion Detective"));
        assertThat(updatedProduct.getImageSrc(), is("tbonkers.com/home.jpg"));
        assertThat(updatedProduct.getPrice(), is(new BigDecimal("180.00")));
    }

    private Product getMockProduct(Long productId) {
        Product mockProduct =  new Product("Teddy Bonkers", "Theodore Bonkers: The Junion Detective", "tbonkers.com/home.jpg", new BigDecimal("180.00"));
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
