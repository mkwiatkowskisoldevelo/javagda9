package com.sda.spring.java11.controller;

import static org.springframework.util.CollectionUtils.isEmpty;

import com.sda.spring.java11.model.Status;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Setter
public class ReceiptSearchParams {

  private String client;

  @Getter
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate endDate;

  @Getter
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate startDate;

  @Getter
  private Set<Status> status;

  public String getClient() {
    if (client == null) {
      return "";
    }
    return this.client;
  }
}
