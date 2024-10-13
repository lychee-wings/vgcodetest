package com.example.vgcodetest.service;

import com.example.vgcodetest.model.GameSales;
import com.example.vgcodetest.model.IngestionHistory;
import com.example.vgcodetest.repository.GameSalesRepository;
import com.example.vgcodetest.repository.IngestionHistoryRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImportService {

  @Autowired
  GameSalesRepository gameSalesRepository;

  @Autowired
  IngestionHistoryRepository ingestionHistoryRepository;

  @Transactional
  public Map<String, Object> importCsv(MultipartFile file) {
    Map<String, Object> summary = new HashMap<>();
    IngestionHistory ih = null;
    try {
      // Store the import history.
      ih = ingestionHistoryRepository.save(
          IngestionHistory.builder().fileName(file.getOriginalFilename())
              .build());
      summary.put("source", ih.getFileName());

      Reader reader = new InputStreamReader(file.getInputStream());

      // parsing the csv records into bean.
      List<GameSales> gameSales = new CsvToBeanBuilder<GameSales>(reader)
          .withType(GameSales.class)//
          .build() //
          .parse();

      // Update the ingestionHistory of each records.
      for (GameSales gs : gameSales) {
        gs.setIngestionHistory(ih);
      }

      // basic save
      List<GameSales> records = gameSalesRepository.saveAll(gameSales);
      summary.put("recordCount", records.size());

      // update the status
      ih.setStatus("success");
      ingestionHistoryRepository.save(ih);
      summary.put("status", ih.getStatus());

    } catch (IOException e) {
      // update the status
      ih.setStatus("failed");
      ingestionHistoryRepository.save(ih);

      summary.put("status", ih.getStatus());
      throw new RuntimeException(e);
    }
    return summary;
  }

}
