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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameSales {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column()
  @CsvBindByName(column = "id")
  private Long id;

  @Min(1)
  @Max(100)
  @Column(name = "game_no")
  @CsvBindByName(column = "game_no")
  private Integer gameNo;

  @Size(max = 20)
  @Column(name = "game_name")
  @CsvBindByName(column = "game_name")
  private String gameName;

  @Size(max = 5)
  @Column(name = "game_code")
  @CsvBindByName(column = "game_code")
  private String gameCode;

  @Min(1)
  @Max(2)
  @Column()
  @CsvBindByName()
  private Integer type;

  @Max(100)
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
