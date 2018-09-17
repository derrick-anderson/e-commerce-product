package com.solstice.ecommerceproduct.service;

import com.solstice.ecommerceproduct.data.ProductRepository;
import com.solstice.ecommerceproduct.domain.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServices {

    private ProductRepository productRepository;

    public ProductServices(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product productIn) {
        return productRepository.save(productIn);
    }

    public Product getOneProduct(Long productId) {
        return productRepository.getOne(productId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long productId) {
        if(getOneProduct(productId) != null){
            productRepository.deleteById(productId);
        }else throw new EntityNotFoundException();
    }

    public Product updateProduct(Long productId, Product productUpdateData) {
        Product savedProduct = getOneProduct(productId);
        if(savedProduct != null){
            if(productUpdateData.getName() != null){
                savedProduct.setName(productUpdateData.getName());
            }
            if(productUpdateData.getDescription() != null){
                savedProduct.setDescription(productUpdateData.getDescription());
            }
            if(productUpdateData.getImageSrc() != null){
                savedProduct.setImageSrc(productUpdateData.getImageSrc());
            }
            if(! productUpdateData.getPrice().equals(BigDecimal.ZERO)){
                savedProduct.setPrice(productUpdateData.getPrice());
            }
            return savedProduct;
        }else throw new EntityNotFoundException();
    }
}
