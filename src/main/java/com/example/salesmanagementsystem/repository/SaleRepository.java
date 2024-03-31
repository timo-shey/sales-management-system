package com.example.salesmanagementsystem.repository;

import com.example.salesmanagementsystem.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
