package com.sda.spring.java11.controller;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.sda.spring.java11.exception.NotFoundException;
import com.sda.spring.java11.model.Product;
import com.sda.spring.java11.service.ProductService;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

  @MockBean
  private ProductService productService;

  @LocalServerPort
  private int port;

  private Product product;

  @Before
  public void setUp() {
    product = new Product(1L, "some-name", 10d);
  }

  @Test
  public void shouldGetProductById() {
    when(productService.getById(1L))
        .thenReturn(product);

    Product result = given()
        .port(port)
        .when()
        .get("/products/" + 1)
        .then()
        .assertThat()
        .statusCode(200)
        .extract()
        .as(Product.class);

    assertEquals(product, result);
  }

  @Test
  public void shouldReturn404IfProductWasNotFound() {
    when(productService.getById(1L))
        .thenThrow(new NotFoundException("Not Found"));

    given()
        .port(port)
        .when()
        .get("/products/" + 1)
        .then()
        .assertThat()
        .statusCode(404);
  }

  @Test
  public void shouldReturnPageOfProducts() {
    String name = "name";
    Double minPrice = 10d;
    Double maxPrice = 100d;
    Pageable pageable = new PageRequest(0, 10);

    when(productService.search(name, minPrice, maxPrice, pageable))
        .thenReturn(new PageImpl<>(Collections.singletonList(product)));

    Page page = given()
        .port(port)
        .when()
        .param("name", name)
        .param("minPrice", minPrice)
        .param("maxPrice", maxPrice)
        .param("page", 0)
        .param("size", 10)
        .get("/products")
        .then()
        .assertThat()
        .statusCode(200)
        .extract()
        .as(Page.class);

    assertEquals(1, page.getTotalElements());
  }
}
