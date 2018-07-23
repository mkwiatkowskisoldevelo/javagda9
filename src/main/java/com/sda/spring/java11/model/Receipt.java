package com.sda.spring.java11.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "receipts")
@Data
public class Receipt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false, name = "client")
  private String client;

  @Past
  @Column(nullable = false, name = "date")
  private LocalDateTime date;

  @NotNull
  @Column(nullable = false, name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

//  @OneToMany(mappedBy = "receipt")
//  private List<Product> products;

  @Size(min = 1)
  //@ManyToMany(cascade = CascadeType.ALL)
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "receipt_products",
      joinColumns = @JoinColumn(name = "receipt_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "product_id", nullable = false))
  private List<Product> products;
}
