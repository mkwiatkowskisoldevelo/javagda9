package com.sda.spring.java11.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "products")
@Data
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Length(min = 1, max = 20)
  @Column(nullable = false, unique = true)
  private String name;

  @Min(0)
  @Max(10000)
  @NotNull
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
