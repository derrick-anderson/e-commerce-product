package com.solstice.ecommerceproduct.controller;

import com.solstice.ecommerceproduct.domain.Product;
import com.solstice.ecommerceproduct.service.ProductServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductServices productServices;

    public ProductController(ProductServices productServices){
        this.productServices = productServices;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productServices.getAllProducts();
    }

    @GetMapping("/products/{productId}")
    public Product getOneProduct(@PathVariable("productId") Long productId){
        return productServices.getOneProduct(productId);
    }

    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("productId") Long productId){
        productServices.deleteProduct(productId);
    }

    @PostMapping("/products")
    public Product saveProduct(@RequestBody Product productIn){
        return productServices.saveProduct(productIn);
    }

    @PutMapping("/products/{productId}")
    public Product updateProduct(@PathVariable("productId") Long productId, @RequestBody Product productToSave){
        return productServices.updateProduct(productId, productToSave);
    }
}
