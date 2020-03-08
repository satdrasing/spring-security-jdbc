package com.satendra.productservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

     private final ProductRepository productRepository;

    Product createProduct(ProductForm productForm) {
        Product _product = Product.builder()
                .name(productForm.getName())
                .description(productForm.getDescription())
                .rating(productForm.getRating())
                .build();

        return productRepository.save(_product);
    }
}
