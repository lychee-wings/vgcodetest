package com.example.vgcodetest.api;

import com.example.vgcodetest.exception.InvalidDateFormatException;
import com.example.vgcodetest.model.GameSales;
import com.example.vgcodetest.service.GameSalesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

  @GetMapping("/dateOfSale")
  public ResponseEntity<Page<GameSales>> findGameSalesByPeriod(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "from") String from,
      @RequestParam(value = "to") String to) {
    Page<GameSales> gameSales = gameSalesService.getGameSalesByDateOfSaleBetween(page, from, to);
    return ResponseEntity.ok().body(gameSales);
  }

  @GetMapping("/salePrice")
  public ResponseEntity<Page<GameSales>> findGameSalesBySalePrice(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "gt") Double greater,
      @RequestParam(value = "lt") Double lesser) {
    Page<GameSales> gameSales = gameSalesService.getGameSalesBySalePrice(page, greater, lesser);
    return ResponseEntity.ok().body(gameSales);
  }

}
