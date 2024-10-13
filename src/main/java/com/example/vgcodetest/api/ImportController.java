package com.example.vgcodetest.api;

import com.example.vgcodetest.model.GameSales;
import com.example.vgcodetest.model.IngestionHistory;
import com.example.vgcodetest.repository.GameSalesRepository;
import com.example.vgcodetest.repository.IngestionHistoryRepository;
import com.example.vgcodetest.service.ImportService;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/import")
@Log4j2
public class ImportController {

  @Autowired
  ImportService importService;

  @PostMapping()
  public ResponseEntity<Object> importCsv(@RequestParam("csv") MultipartFile file) {

    Map<String, Object> summary = importService.importCsv(file);

    return ResponseEntity.status(HttpStatus.CREATED).body(summary);
  }
}
