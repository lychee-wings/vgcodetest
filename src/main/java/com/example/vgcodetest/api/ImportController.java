package com.example.vgcodetest.api;

import com.example.vgcodetest.service.ImportService;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/import")
public class ImportController {

  @Autowired
  ImportService importService;

  @PostMapping()
  public ResponseEntity<Object> importCsv(
      @RequestParam(value = "csv", required = true) MultipartFile file) {

    Map<String, Object> summary = importService.importCsv(file);

    return ResponseEntity.status(HttpStatus.CREATED).body(summary);
  }
}
