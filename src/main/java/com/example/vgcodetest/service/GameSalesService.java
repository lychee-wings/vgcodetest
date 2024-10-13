package com.example.vgcodetest.service;

import com.example.vgcodetest.exception.InvalidDateFormatException;
import com.example.vgcodetest.exception.InvalidTimePeriodException;
import com.example.vgcodetest.model.GameSales;
import com.example.vgcodetest.repository.GameSalesRepository;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

  static final int PAGE_SIZE = 100;

//  private DateTimeFormatter format = DateTimeFormatter.ofPattern();

  public List<GameSales> getGameSales() {

    return gameSalesRepository.findAll();
  }

  public Page<GameSales> getGameSales(int page) {
    Pageable pageable = PageRequest.of(page, PAGE_SIZE);

    return gameSalesRepository.findAll(pageable);
  }

  public Page<GameSales> getGameSalesByDateOfSaleBetween(int page, String start, String end) {

    Timestamp tsStart, tsEnd;
    try {

      tsStart = Timestamp.valueOf(
          LocalDate.parse(start, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay());
      tsEnd = Timestamp.valueOf(
          LocalDate.parse(end, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay());

      if (!tsStart.before(tsEnd)) {
        throw new InvalidTimePeriodException();
      }

      Pageable pageable = PageRequest.of(page, PAGE_SIZE);
      Page<GameSales> byDateOfSaleBetween = gameSalesRepository.findByDateOfSaleBetween(pageable,
          tsStart, tsEnd);

      return byDateOfSaleBetween;

    } catch (DateTimeParseException e) {
      throw new InvalidDateFormatException();
    }
  }

  public Page<GameSales> getGameSalesBySalePrice(int page, Double greater, Double lesser) {

    return null;
  }
}
