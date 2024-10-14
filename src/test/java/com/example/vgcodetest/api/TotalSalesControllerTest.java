package com.example.vgcodetest.api;

import static com.example.vgcodetest.service.TotalSalesService.TOTAL_NUMBER_OF_GAMES_SOLD;
import static com.example.vgcodetest.service.TotalSalesService.TOTAL_SALES;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.vgcodetest.model.GameSales;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql({"/schema-H2.sql", "/data-H2.sql"})
class TotalSalesControllerTest {

  @Autowired
  private MockMvc client;

  @Autowired
  private Environment environment;

  private ObjectMapper mapper = new ObjectMapper();

  private GameSales gs1 = new GameSales(10001L, 90, "Assassin's Creed III", "PS3", 2, 59.0, 0.09,
      64.31,
      Timestamp.valueOf("2024-04-01 10:00:00"),
      null);

  private GameSales gs2 = new GameSales(10002L, 80, "Fallout 4", "PS4", 2, 70.0, 0.09,
      76.3,
      Timestamp.valueOf("2024-04-02 11:00:00"),
      null);

  @Test
  void findGameCountByPeriod() throws Exception {

    System.out.println("QR Debug: " + Arrays.toString(environment.getActiveProfiles()));

    MvcResult mvcResult = client.perform(get("/games-sold/dateOfSale")
        .param("from", "2024-04-01")
        .param("to", "2024-04-02")
    ).andExpect(status().isOk()).andReturn();

    // Map the response to JsonNode for the ease of getting the content(s).
    JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsString());

    // Check has 1 element match
    MatcherAssert.assertThat(response.get(TOTAL_NUMBER_OF_GAMES_SOLD).intValue(),
        Matchers.equalTo(1));
  }

  @Test
  void findTotalSalesByPeriod() throws Exception {
    System.out.println("QR Debug: " + Arrays.toString(environment.getActiveProfiles()));

    MvcResult mvcResult = client.perform(get("/total-sales/dateOfSale")
        .param("from", "2024-04-01")
        .param("to", "2024-04-03")
    ).andExpect(status().isOk()).andReturn();

    // Map the response to JsonNode for the ease of getting the content(s).
    JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsString());

    // Check has 2 elements sum
    MatcherAssert.assertThat(response.get(TOTAL_SALES).asDouble(),
        Matchers.equalTo(gs1.getSalePrice() + gs2.getSalePrice()));
  }

  @Test
  void findTotalSalesByInvalidPeriod() throws Exception {
    MvcResult mvcResult = client.perform(get("/total-sales/dateOfSale")
        .param("from", "2024-04-01")
        .param("to", "2023-04-03")
    ).andExpect(status().isBadRequest()).andReturn();

    // Map the response to JsonNode for the ease of getting the content(s).
    Map<String, Object> out = mapper.readValue(mvcResult.getResponse().getContentAsString(),
        new TypeReference<Map<String, Object>>() {
        });

    // Check the response body fields.
    MatcherAssert.assertThat(out.get("statusCode"), Matchers.equalTo(400));
    MatcherAssert.assertThat(out.get("reason"),
        Matchers.equalTo("Time period [from] MUST before [to]"));
  }

  @Test
  void findTotalSalesByGameNoWithinPeriod() throws Exception {
    MvcResult mvcResult = client.perform(
        get(String.format("/total-sales/gameNo/%d/dateOfSale", gs1.getGameNo()))
            .param("from", "2024-04-01")
            .param("to", "2024-04-03")
    ).andExpect(status().isOk()).andReturn();

    // Map the response to JsonNode for the ease of getting the content(s).
    JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsString());

    // Check has 2 elements sum
    MatcherAssert.assertThat(response.get(TOTAL_SALES).asDouble(),
        Matchers.equalTo(gs1.getSalePrice()));
  }

  @Test
  void findTotalSalesByGameNoWithinPeriodNotExist() throws Exception {
    MvcResult mvcResult = client.perform(
        get(String.format("/total-sales/gameNo/%d/dateOfSale", 1000))
            .param("from", "2024-04-01")
            .param("to", "2024-04-03")
    ).andExpect(status().isOk()).andReturn();

    // Map the response to JsonNode for the ease of getting the content(s).
    JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsString());

    // Check has 2 elements sum
    MatcherAssert.assertThat(response.get(TOTAL_SALES).asDouble(),
        Matchers.equalTo(0.0));
  }
}