package com.example.vgcodetest.api;

import com.example.vgcodetest.model.GameSales;
import com.example.vgcodetest.service.GameSalesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game-sales")
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

  @GetMapping(value = "/salePrice", params = {"lt"})
  public ResponseEntity<Page<GameSales>> findGameSalesBySalePriceLessThan(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "lt") Double lesser) {
    Page<GameSales> gameSales = gameSalesService.getGameSalesBySalePriceLessThan(page, lesser);
    return ResponseEntity.ok().body(gameSales);
  }

  @GetMapping(value = "/salePrice", params = {"gt"})
  public ResponseEntity<Page<GameSales>> findGameSalesBySalePriceGreaterThan(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "gt") Double greater) {
    Page<GameSales> gameSales = gameSalesService.getGameSalesBySalePriceGreaterThan(page, greater);
    return ResponseEntity.ok().body(gameSales);
  }

}
