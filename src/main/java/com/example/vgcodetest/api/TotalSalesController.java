package com.example.vgcodetest.api;

import com.example.vgcodetest.service.TotalSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TotalSalesController {

  @Autowired
  TotalSalesService totalSalesService;

  @GetMapping("/games-sold/dateOfSale")
  public ResponseEntity<Object> findGameCountByPeriod(
      @RequestParam(value = "from") String from,
      @RequestParam(value = "to") String to) {

    return ResponseEntity.ok().body(totalSalesService.getGameCountByDateOfSaleBetween(from, to));
  }

  @GetMapping("/total-sales/dateOfSale")
  public ResponseEntity<Object> findTotalSalesByPeriod(
      @RequestParam(value = "from") String from,
      @RequestParam(value = "to") String to) {

    return ResponseEntity.ok().body(totalSalesService.getTotalSalesByDateOfSaleBetween(from, to));
  }

  @GetMapping("/total-sales/gameNo/{gameNo}/dateOfSale")
  public ResponseEntity<Object> findTotalSalesByGameNoWithinPeriod(
      @PathVariable int gameNo,
      @RequestParam(value = "from") String from,
      @RequestParam(value = "to") String to) {

    return ResponseEntity.ok()
        .body(totalSalesService.getTotalSalesByGameNoAndDateOfSaleBetween(gameNo, from, to));
  }

}
