package com.example.vgcodetest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "game_sales")
public class GameSales {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column()
  @CsvBindByName(column = "id")
  private Long id;

  @Min(value = 1, message = "game_no has exceeded limit!")
  @Max(value = 100, message = "game_no has exceeded limit!")
  @Column(name = "game_no")
  @CsvBindByName(column = "game_no")
  private Integer gameNo;

  @Size(max = 20, message = "game_name has exceeded limit!")
  @Column(name = "game_name")
  @CsvBindByName(column = "game_name")
  private String gameName;

  @Size(max = 5, message = "game_code has exceeded limit!")
  @Column(name = "game_code")
  @CsvBindByName(column = "game_code")
  private String gameCode;

  @Min(value = 1, message = "unknown type!")
  @Max(value = 2,  message = "unknown type!")
  @Column(name = "game_type")
  @CsvBindByName()
  private Integer type;

  @Max(value = 100, message = "cost_price exceeded limit!")
  @Column(name = "cost_price")
  @CsvBindByName(column = "cost_price")
  private Double costPrice;

  @Column()
  @CsvBindByName()
  private Double tax = 0.09;

  @Column(name = "sale_price")
  @CsvBindByName(column = "sale_price")
  private Double salePrice;

  @Column(name = "date_of_sale")
  @CsvBindByName(column = "date_of_sale")
  private Timestamp dateOfSale;


  @OneToOne
  @JoinColumn(name = "ingestion_id")
  private IngestionHistory ingestionHistory;

}
