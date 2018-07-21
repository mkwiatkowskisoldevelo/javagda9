package com.sda.spring.java11.service;

import com.sda.spring.java11.exception.NotFoundException;
import com.sda.spring.java11.model.Product;
import com.sda.spring.java11.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  public Product create(Product product) {
    return productRepository.save(product);
  }

  public List<Product> getAll() {
    return productRepository.findAll();
  }

  public List<Product> search(String name, Double minPrice, Double maxPrice) {
    return productRepository
        .findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualOrderByPriceDesc(
            name, minPrice, maxPrice);
  }

  public void delete(Long id) {
    if (!productRepository.existsById(id)) {
      throw new NotFoundException(String.format("Product with id %s does not exists", id));
    }
    productRepository.deleteById(id);
  }

  public Product getById(Long id) {
    Optional<Product> product = productRepository.findById(id);
    if (!product.isPresent()) {
      throw new NotFoundException(String.format("Product with id %s does not exists", id));
    }
    return product.get();
  }

  public Product update(Long id, Product product) {
    Optional<Product> savedProduct = productRepository.findById(id);
    if (!savedProduct.isPresent()) {
      throw new NotFoundException(String.format("Product with id %s does not exists", id));
    }
    Product dbProduct = savedProduct.get();
    dbProduct.updateFrom(product);
    return productRepository.save(dbProduct);
  }
}
