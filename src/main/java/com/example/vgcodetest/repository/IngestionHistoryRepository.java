package com.example.vgcodetest.repository;

import com.example.vgcodetest.model.GameSales;
import com.example.vgcodetest.model.IngestionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngestionHistoryRepository extends JpaRepository<IngestionHistory, Long> {


}
