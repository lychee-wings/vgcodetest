package com.example.vgcodetest.repository;

import com.example.vgcodetest.model.GameSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSalesRepository extends JpaRepository<GameSales, Long> {


}
