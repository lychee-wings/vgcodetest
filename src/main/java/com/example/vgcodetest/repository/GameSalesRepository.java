package com.example.vgcodetest.repository;

import com.example.vgcodetest.model.GameSales;
import java.sql.Timestamp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSalesRepository extends JpaRepository<GameSales, Long> {

  Page<GameSales> findByDateOfSaleBetween(Pageable pageable, Timestamp start, Timestamp end);
  Page<GameSales> findBySalePriceLessThan(Pageable pageable, Double lesser);
  Page<GameSales> findBySalePriceGreaterThan(Pageable pageable, Double greater);

  @Query(value = "SELECT COUNT(*)\n"
                 + "FROM #{#entityName} gs WHERE gs.date_of_sale between ?1 and ?2;", nativeQuery = true)
  Integer queryGameCountBetween(Timestamp start, Timestamp end);

  @Query(value = "SELECT SUM(gs.sale_price)\n"
                 + "FROM #{#entityName} gs WHERE gs.date_of_sale between ?1 and ?2;", nativeQuery = true)
  Double queryGameSalesBetween(Timestamp start, Timestamp end);

  @Query(value = "SELECT SUM(gs.sale_price)\n"
                 + "FROM #{#entityName} gs WHERE gs.game_no = ?1 AND gs.date_of_sale between ?2 and ?3;", nativeQuery = true)
  Double queryGameNoAndGameSalesBetween(int gameNo, Timestamp start, Timestamp end);

}
