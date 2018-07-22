package com.sda.spring.java11.service;

import com.sda.spring.java11.exception.BindingResultException;
import com.sda.spring.java11.exception.NotFoundException;
import com.sda.spring.java11.model.Product;
import com.sda.spring.java11.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class ProductService {

  @Autowired
  private ProductRepository productRepository;

  public Product create(Product product, BindingResult bindingResult) {
    validateProduct(product, null, bindingResult);
    return productRepository.save(product);
  }

  public List<Product> getAll() {
    return productRepository.findAll();
  }

  public Page<Product> search(String name, Double minPrice, Double maxPrice, Pageable pageable) {
    return productRepository
        .findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualOrderByPriceDesc(
            name, minPrice, maxPrice, pageable);
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

  public Product update(Long id, Product product, BindingResult bindingResult) {
    Optional<Product> savedProduct = productRepository.findById(id);
    if (!savedProduct.isPresent()) {
      throw new NotFoundException(String.format("Product with id %s does not exists", id));
    }
    Product dbProduct = savedProduct.get();

    validateProduct(product, dbProduct, bindingResult);

    dbProduct.updateFrom(product);
    return productRepository.save(dbProduct);
  }

  private void validateProduct(Product product, Product dbProduct, BindingResult bindingResult) {
    if (
        ( // update, podany name jest rozny od tego zapisanego w bazie
            dbProduct != null
            && productRepository.existsByName(product.getName())
            && !dbProduct.getName().equals(product.getName())
        ) ||
        ( // create, podany name juz istnieje w bazie
          dbProduct == null
          && productRepository.existsByName(product.getName())
        ))
    {
      FieldError error = new FieldError(
          "product",
          "name",
          String.format("Product name %s already exists!", product.getName()));
      bindingResult.addError(error);
    }
    if (bindingResult.hasErrors()) {
      throw new BindingResultException(bindingResult);
    }
  }
}
