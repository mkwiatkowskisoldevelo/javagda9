package com.sda.spring.java11.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sda.spring.java11.exception.BindingResultException;
import com.sda.spring.java11.exception.NotFoundException;
import com.sda.spring.java11.model.Product;
import com.sda.spring.java11.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

  @Autowired
  private ProductService productService;

  @MockBean
  private ProductRepository productRepository;

  private Product product;

  @Rule
  public final ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() {
    product = new Product(1L, "product-1", 10.99d);
  }

  @Test
  public void shouldGetProductById() {
    when(productRepository.findById(1L))
        .thenReturn(Optional.of(product));
    assertEquals(product, productService.getById(1L));
  }

  @Test(expected = NotFoundException.class)
  public void shouldThrowExceptionIfProductDoesNotExists() {
    when(productRepository.findById(1L))
        .thenReturn(Optional.empty());
    productService.getById(1L);
  }

  @Test
  public void shouldThrowExceptionWithMessageIfProductDoesNotExists() {
    expectedException.expect(NotFoundException.class);
    expectedException.expectMessage("Product with id 1 does not exists");

    when(productRepository.findById(1L))
        .thenReturn(Optional.empty());
    productService.getById(1L);
  }

  @Test
  public void shouldSearchWithProperParams() {
    List<Product> products = new ArrayList<>();
    products.add(product);
    Page<Product> productPage = new PageImpl<>(products);

    when(productRepository
        .findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualOrderByPriceDesc(
            any(String.class), any(Double.class), any(Double.class), any(Pageable.class)
        )).thenReturn(productPage);

    assertEquals(productPage, productService.search("some-name", 0d, 100d, new PageRequest(0, 10)));

    verify(productRepository, times(1))
        .findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualOrderByPriceDesc(
            "some-name", 0d, 100d, new PageRequest(0, 10));
  }

  @Test
  public void shouldSetMaxPriceToDoubleMaxValueIfNull() {
    List<Product> products = new ArrayList<>();
    products.add(product);
    Page<Product> productPage = new PageImpl<>(products);

    when(productRepository
        .findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualOrderByPriceDesc(
            any(String.class), any(Double.class), eq(Double.MAX_VALUE), any(Pageable.class)
        )).thenReturn(productPage);

    assertEquals(productPage, productService.search("some-name", 0d, null, new PageRequest(0, 10)));

    verify(productRepository, times(1))
        .findByNameContainingIgnoreCaseAndPriceGreaterThanEqualAndPriceLessThanEqualOrderByPriceDesc(
            "some-name", 0d, Double.MAX_VALUE, new PageRequest(0, 10));
  }

  @Test
  public void shouldCreateProduct() {
    when(productRepository.existsByName(eq(product.getName())))
      .thenReturn(false);
    productService.create(product, new BeanPropertyBindingResult(product, "product"));
    verify(productRepository, times(1)).save(product);
  }

  @Test(expected = BindingResultException.class)
  public void shouldNotCreateProduct() {
    when(productRepository.existsByName(eq(product.getName())))
        .thenReturn(true);
    productService.create(product, new BeanPropertyBindingResult(product, "product"));
    verify(productRepository, never()).save(product);
  }
}
