package com.satendra.productservice;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    final ProductRepository productRepository;

    final ProductService productService;

    final UserDetailsManager userDetailsManager;

    @GetMapping()
    @JsonView(View.Summary.class)
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> product = productRepository.findAll();
        return ok(product);
    }

   @PostMapping()
    public ResponseEntity<Void> createProduct(@RequestBody ProductForm productForm, HttpServletRequest request) {
        Product product = productService.createProduct(productForm);
        URI createdUri = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path("/product/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return created(createdUri).build();
    }


}
