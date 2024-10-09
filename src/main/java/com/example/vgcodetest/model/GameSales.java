package com.example.vgcodetest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.sql.Timestamp;
import lombok.Data;

@Entity
@Data
public class GameSales {

  @Id
//  @Column(name = "id")
  private long id;
  @Min(1)
  @Max(100)
  @Column(name = "game_no")
  private int gameNo;
  @Size(max = 20)
  @Column(name = "game_name")
  private String gameName;
  @Size(max = 5)
  @Column(name = "game_code")
  private String gameCode;
  @Min(1)
  @Max(2)
//  @Column(name = "type")
  private int type;
  @Max( 100)
  @Column(name = "cost_price")
  private double costPrice;
//  @Column(name = "tax")
  private double tax = 0.09;
  @Column(name = "sale_price")
  private double salePrice;
  @Column(name = "date_of_sale")
  private Timestamp dateOfSale;

}
