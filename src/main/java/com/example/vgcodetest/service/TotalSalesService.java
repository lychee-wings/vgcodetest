package com.example.vgcodetest.service;

import com.example.vgcodetest.exception.InvalidDateFormatException;
import com.example.vgcodetest.exception.InvalidTimePeriodException;
import com.example.vgcodetest.repository.GameSalesRepository;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TotalSalesService {

  public static final String TOTAL_NUMBER_OF_GAMES_SOLD = "totalNumberOfGamesSold";
  public static final String TOTAL_SALES = "totalSales";
  @Autowired
  private GameSalesRepository gameSalesRepository;

  public Map<String, Object> getGameCountByDateOfSaleBetween(String start, String end) {
    Timestamp tsStart, tsEnd;
    try {

      tsStart = Timestamp.valueOf(
          LocalDate.parse(start, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay());
      tsEnd = Timestamp.valueOf(
          LocalDate.parse(end, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay());

      if (!tsStart.before(tsEnd)) {
        throw new InvalidTimePeriodException();
      }

      // Total number of games sold = COUNT(*) between from <==> to
      int count = gameSalesRepository.queryGameCountBetween(tsStart, tsEnd);

      return Map.of(TOTAL_NUMBER_OF_GAMES_SOLD, count);

    } catch (DateTimeParseException e) {
      throw new InvalidDateFormatException();
    }
  }

  public Map<String, Object> getTotalSalesByDateOfSaleBetween(String start, String end) {
    Timestamp tsStart, tsEnd;
    try {

      tsStart = Timestamp.valueOf(
          LocalDate.parse(start, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay());
      tsEnd = Timestamp.valueOf(
          LocalDate.parse(end, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay());

      if (!tsStart.before(tsEnd)) {
        throw new InvalidTimePeriodException();
      }

      // Total number of games sold = SUM(sale_price) between from <==> to
      Double sales = gameSalesRepository.queryGameSalesBetween(tsStart, tsEnd);

      if (sales == null) {
        sales = 0.0;
      }

      return Map.of(TOTAL_SALES, Double.valueOf(new DecimalFormat("0.00").format(sales)));

    } catch (DateTimeParseException e) {
      throw new InvalidDateFormatException();
    }
  }

  public Map<String, Object> getTotalSalesByGameNoAndDateOfSaleBetween(int gameNo, String start,
      String end) {
    Timestamp tsStart, tsEnd;
    try {

      tsStart = Timestamp.valueOf(
          LocalDate.parse(start, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay());
      tsEnd = Timestamp.valueOf(
          LocalDate.parse(end, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay());

      if (!tsStart.before(tsEnd)) {
        throw new InvalidTimePeriodException();
      }

      // Total number of games sold = SUM(sale_price) between from <==> to
      Double sales = gameSalesRepository.queryGameNoAndGameSalesBetween(gameNo, tsStart, tsEnd);

      if (sales == null) {
        sales = 0.0;
      }

      return Map.of(TOTAL_SALES, Double.valueOf(new DecimalFormat("0.00").format(sales)));

    } catch (DateTimeParseException e) {
      throw new InvalidDateFormatException();
    }
  }

}
