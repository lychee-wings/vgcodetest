package com.example.vgcodetest.api;

import com.example.vgcodetest.model.GameSales;
import com.example.vgcodetest.model.IngestionHistory;
import com.example.vgcodetest.repository.GameSalesRepository;
import com.example.vgcodetest.repository.IngestionHistoryRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/import")
@Log4j2
public class ImportService {

  @Autowired
  GameSalesRepository gameSalesRepository;

  @Autowired
  IngestionHistoryRepository ingestionHistoryRepository;

  @PostMapping()
  public ResponseEntity<Object> importCsv(@RequestParam("csv") MultipartFile file) {

    try {
      // Store the import history.
      IngestionHistory ih = ingestionHistoryRepository.save(
          IngestionHistory.builder().source(file.getOriginalFilename()).build());

      Reader reader = new InputStreamReader(file.getInputStream());

      // parsing the csv records into bean.
      List<GameSales> gameSales = new CsvToBeanBuilder<GameSales>(reader)
          .withType(GameSales.class)//
          .build() //
          .parse();

      gameSales.forEach(g -> g.setIngestionHistory(ih));

      // basic save
      gameSalesRepository.saveAll(gameSales);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return null;
  }
}
