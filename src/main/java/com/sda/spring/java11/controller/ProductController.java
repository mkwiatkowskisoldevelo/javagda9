package com.sda.spring.java11.controller;

import com.sda.spring.java11.model.Product;
import com.sda.spring.java11.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Product create(@RequestBody Product product) {
    return productService.create(product);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Product> search(
      @RequestParam(value = "name", defaultValue = "") String name,
      @RequestParam(value = "minPrice", defaultValue = "0") Double minPrice,
      @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
    if (maxPrice == null) {
      maxPrice = Double.MAX_VALUE;
    }
    return productService.search(name, minPrice, maxPrice);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    productService.delete(id);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Product getById(@PathVariable Long id) {
    return productService.getById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Product update(
      @PathVariable Long id,
      @RequestBody Product product) {
    return productService.update(id, product);
  }
}
