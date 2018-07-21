package com.sda.spring.java11.controller;

import com.sda.spring.java11.model.Product;
import com.sda.spring.java11.model.Receipt;
import com.sda.spring.java11.model.Status;
import com.sda.spring.java11.service.ReceiptService;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

  @Autowired
  private ReceiptService receiptService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Receipt create(@RequestBody Receipt receipt) {
    return receiptService.create(receipt);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Receipt> search(
      @RequestParam(defaultValue = "") String client,
      @RequestParam(required = false)
      @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
      @RequestParam(required = false)
      @DateTimeFormat(iso = ISO.DATE) LocalDate endDate,
      @RequestParam(required = false) Set<Status> status) {
//    return receiptService.searchByClient(client);
    return receiptService.search(client, startDate, endDate, status);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    receiptService.delete(id);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Receipt getById(@PathVariable Long id) {
    return receiptService.getById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Receipt update(
      @PathVariable Long id,
      @RequestBody Receipt receipt) {
    return receiptService.update(id, receipt);
  }
}