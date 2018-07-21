package com.sda.spring.java11.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private Double price;

//  @ManyToOne
//  @JoinColumn(name = "receipt_id")
//  private Receipt receipt;

  public void updateFrom(Product product) {
    if (product.getName() != null) {
      this.name = product.getName();
    }
    if (product.getPrice() != null) {
      this.price = product.getPrice();
    }
  }
}
