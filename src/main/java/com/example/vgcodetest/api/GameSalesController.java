package com.example.vgcodetest.api;

import com.example.vgcodetest.model.GameSales;
import com.example.vgcodetest.service.GameSalesService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game-sales")
@Log4j2
public class GameSalesController {

  @Autowired
  GameSalesService gameSalesService;

  @GetMapping()
  public ResponseEntity<Page<GameSales>> findPaginatedGameSales(
      @RequestParam(value = "page", defaultValue = "0") int page) {
    Page<GameSales> gameSales = gameSalesService.getGameSales(page);
    return ResponseEntity.ok().body(gameSales);
  }

}
