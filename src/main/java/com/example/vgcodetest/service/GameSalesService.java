package com.example.vgcodetest.service;

import com.example.vgcodetest.model.GameSales;
import com.example.vgcodetest.repository.GameSalesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GameSalesService {

  @Autowired
  private GameSalesRepository gameSalesRepository;

  public List<GameSales> getGameSales() {

    return gameSalesRepository.findAll();
  }

  public Page<GameSales> getGameSales(int page) {
    Pageable pageable = PageRequest.of(page, 100);

    return gameSalesRepository.findAll(pageable);
  }
}
