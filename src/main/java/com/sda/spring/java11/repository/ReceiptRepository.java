package com.sda.spring.java11.repository;

import com.sda.spring.java11.model.Receipt;
import com.sda.spring.java11.model.Status;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

  List<Receipt> findByClientContainingAndDateGreaterThanEqualAndDateLessThanEqual(
      String client,
      LocalDateTime startDate,
      LocalDateTime endDate);
  List<Receipt> findByClientContainingAndDateBetween(
      String client,
      LocalDateTime startDate,
      LocalDateTime endDate);
  List<Receipt> findByClientContainingAndDateBetweenAndStatus(
      String client,
      LocalDateTime startDate,
      LocalDateTime endDate,
      Status status);
  List<Receipt> findByClientContainingAndDateBetweenAndStatusIn(
      String client,
      LocalDateTime startDate,
      LocalDateTime endDate,
      Set<Status> statuses);

  @Query(
      value = "SELECT * FROM receipts as r WHERE r.client LIKE %?1%",
      nativeQuery = true)
  List<Receipt> search(String name);
}
