package com.example.vgcodetest.api;

import com.example.vgcodetest.repository.GameSalesRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game-sales")
@Log4j2
public class GameSalesService {

  @Autowired
  GameSalesRepository gameSalesRepository;

  @GetMapping
  public ResponseEntity<Object> getGameSales() {

    return ResponseEntity.ok().body(gameSalesRepository.findAll());
  }

}
