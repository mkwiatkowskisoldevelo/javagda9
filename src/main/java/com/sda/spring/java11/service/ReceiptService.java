package com.sda.spring.java11.service;

import static org.springframework.util.CollectionUtils.isEmpty;

import com.sda.spring.java11.exception.NotFoundException;
import com.sda.spring.java11.model.Receipt;
import com.sda.spring.java11.model.Status;
import com.sda.spring.java11.repository.ReceiptRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

  @Autowired
  private ReceiptRepository receiptRespository;

  public Receipt create(Receipt receipt) {
    return receiptRespository.save(receipt);
  }

  public List<Receipt> searchByClient(String client) {
    return receiptRespository.search(client);
  }

  public List<Receipt> getAll() {
    return receiptRespository.findAll();
  }

  public Page<Receipt> search(
      String client,
      LocalDate startDate,
      LocalDate endDate,
      Set<Status> statuses,
      Pageable pageable) {
    if (startDate == null) {
      startDate = LocalDate.of(2000, 1, 1);
    }
    if (endDate == null) {
      endDate = LocalDate.of(2020, 1, 1);
    }

    if (isEmpty(statuses)) {
      Set<Status> allStatuses = new HashSet<>();
      allStatuses.add(Status.INITIATED);
      allStatuses.add(Status.SUBMITTED);
      allStatuses.add(Status.DEVIVERED);
      statuses = allStatuses;
    }

    return receiptRespository.findByClientContainingAndDateBetweenAndStatusIn(
        client,
        startDate.atStartOfDay(),
        endDate.atTime(LocalTime.MAX),
        statuses,
        pageable);
  }

  public void delete(Long id) {
    if (!receiptRespository.existsById(id)) {
      throw new NotFoundException(
          String.format("Receipt with id %s does not exists!", id));
    }
    receiptRespository.deleteById(id);
  }

  public Receipt getById(Long id) {
    Optional<Receipt> receipt = receiptRespository.findById(id);
    if (!receipt.isPresent()) {
      throw new NotFoundException(
          String.format("Receipt with id %s does not exists!", id));
    }
    return receipt.get();
  }

  public Receipt update(Long id, Receipt receipt) {
    if (!receiptRespository.existsById(id)) {
      throw new NotFoundException(
          String.format("Receipt with id %s does not exists!", id));
    }
    receipt.setId(id);
    return receiptRespository.save(receipt);
  }
}
