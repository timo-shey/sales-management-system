package com.example.salesmanagementsystem.repository;

import com.example.salesmanagementsystem.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {

}
