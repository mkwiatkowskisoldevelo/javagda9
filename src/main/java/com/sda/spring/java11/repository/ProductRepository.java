package com.sda.spring.java11.repository;

import com.sda.spring.java11.model.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findByName(String name);
  List<Product> findByNameContaining(String name);
  List<Product> findByNameContainingIgnoreCase(String name);
  List<Product> findByNameContainingIgnoreCaseAndPrice(
      String name, Double minPrice);
  List<Product> findByNameContainingIgnoreCaseAndPriceGreaterThanEqual(
      String name, Double minPrice);
  List<Product> findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
      String name, Double minPrice, Double maxPrice);
  List<Product>
  findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualOrderByPriceDesc(
      String name, Double minPrice, Double maxPrice);
  Page<Product>
  findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualOrderByPriceDesc(
      String name, Double minPrice, Double maxPrice, Pageable pageable);

  Page<Product>
  findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqual(
      String name, Double minPrice, Double maxPrice, Pageable pageable);

  boolean existsByName(String name);
}
