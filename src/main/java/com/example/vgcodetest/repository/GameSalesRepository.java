package com.example.vgcodetest.repository;

import com.example.vgcodetest.model.GameSales;
import java.sql.Timestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSalesRepository extends JpaRepository<GameSales, Long> {

  Page<GameSales> findByDateOfSaleBetween(Pageable pageable, Timestamp start, Timestamp end);
  Page<GameSales> findBySalePriceLessThan(Pageable pageable, Double lesser);
  Page<GameSales> findBySalePriceGreaterThan(Pageable pageable, Double greater);

}
