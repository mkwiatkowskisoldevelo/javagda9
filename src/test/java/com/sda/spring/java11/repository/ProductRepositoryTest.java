package com.sda.spring.java11.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

import com.sda.spring.java11.model.Product;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  private Product product;

  @Before
  public void setUp() {
    productRepository.deleteAll();
    productRepository.flush();
    product = productRepository.save(new Product(null, "name", 10d));
  }

  @Test
  public void shouldGetProductById() {
    assertEquals(product, productRepository.findById(product.getId()).get());
  }

  @Test
  public void shouldFindProductsWithNameContaining() {
    Product product1 = productRepository.save(new Product(null, "NAME-something", 10d));
    Product product2 = productRepository.save(new Product(null, "asd-nAmE", 10d));
    Product product3 = productRepository.save(new Product(null, "name-asd", 10d));
    Product product4 = productRepository.save(new Product(null, "something-else", 10d));

    List<Product> products = productRepository.findByNameContaining("name");

    assertThat(products, hasItems(product, product1, product2, product3));
  }
}
