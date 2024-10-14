package com.example.vgcodetest.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.vgcodetest.model.GameSales;
import com.example.vgcodetest.model.IngestionHistory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GameSalesControllerTest {

  @Autowired
  private MockMvc client;
  private ObjectMapper mapper = new ObjectMapper();

  private GameSales gs1 = new GameSales(10001L, 90, "Assassin's Creed III", "PS3", 2, 59.0, 0.09,
      64.31,
      Timestamp.valueOf("2024-04-01 10:00:00"),
      IngestionHistory.builder().id(1L).fileName("unitTest").status("success").build());

  private GameSales gs2 = new GameSales(10002L, 80, "Fallout 4", "PS4", 2, 70.0, 0.09,
      76.3,
      Timestamp.valueOf("2024-04-02 11:00:00"),
      IngestionHistory.builder().id(1L).fileName("unitTest").status("success").build());


  /**
   * Positive test case that only check the sending of the api with status OK.
   *
   * @throws Exception
   */
  @Test
  void getGameSales() throws Exception {
    MvcResult mvcResult = client.perform(get("/game-sales")).andExpect(status().isOk()).andReturn();

    // Map the response to JsonNode for the ease of getting the content(s).
    JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsString());

    // parsing to get the content for page.
    List<GameSales> out = mapper.treeToValue(response.get("content"),
        new TypeReference<List<GameSales>>() {
        });

    // Take 1 sample in the init script for validation.
    MatcherAssert.assertThat(out, Matchers.containsInAnyOrder(gs1, gs2));

    // To check the page size is 100
    MatcherAssert.assertThat(response.get("size").intValue(), Matchers.equalTo(100));
  }

  @Test
  void getGameSalesByPeriod() throws Exception {
    MvcResult mvcResult = client.perform(get("/game-sales/dateOfSale")
        .param("from", "2024-04-01")
        .param("to", "2024-04-02")
    ).andExpect(status().isOk()).andReturn();

    // Map the response to JsonNode for the ease of getting the content(s).
    JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsString());

    // parsing to get the content for page.
    List<GameSales> out = mapper.treeToValue(response.get("content"),
        new TypeReference<List<GameSales>>() {
        });

    // Check has 1 element match
    MatcherAssert.assertThat(out, Matchers.contains(gs1));
    MatcherAssert.assertThat(response.get("totalElements").intValue(), Matchers.equalTo(1));
  }

  @Test
  void getGameSalesByPeriodNotFound() throws Exception {
    MvcResult mvcResult = client.perform(get("/game-sales/dateOfSale")
        .param("from", "2024-05-01")
        .param("to", "2024-05-02")
    ).andExpect(status().isOk()).andReturn();

    // Map the response to JsonNode for the ease of getting the content(s).
    JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsString());

    // parsing to get the content for page.
    List<GameSales> out = mapper.treeToValue(response.get("content"),
        new TypeReference<List<GameSales>>() {
        });

    // Check has zero element match.
    MatcherAssert.assertThat(out, Matchers.hasSize(0));
    MatcherAssert.assertThat(response.get("totalElements").intValue(), Matchers.equalTo(0));
  }

  @Test
  void getGameSalesByPeriodInvalidDateFormat() throws Exception {
    MvcResult mvcResult = client.perform(get("/game-sales/dateOfSale")
        .param("from", "2024-05-010")
        .param("to", "2024-05-02")
    ).andExpect(status().isBadRequest()).andReturn();

    // Map the response to JsonNode for the ease of getting the content(s).
    Map<String, Object> out = mapper.readValue(mvcResult.getResponse().getContentAsString(),
        new TypeReference<Map<String, Object>>() {
        });

    // Check the response body fields.
    MatcherAssert.assertThat(out.get("statusCode"), Matchers.equalTo(400));
    MatcherAssert.assertThat(out.get("reason"),
        Matchers.equalTo("Invalid Date Format. Please use yyyy-MM-dd!"));
  }

  @Test
  void getGameSalesByPeriodFromBiggerThanTo() throws Exception {
    MvcResult mvcResult = client.perform(get("/game-sales/dateOfSale")
        .param("from", "2024-05-01")
        .param("to", "2023-04-02")
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
  void findGameSalesBySalePriceLessThan() throws Exception {
    MvcResult mvcResult = client.perform(get("/game-sales/salePrice")
        .param("lt", "70.0")
    ).andExpect(status().isOk()).andReturn();

    // Map the response to JsonNode for the ease of getting the content(s).
    JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsString());

    // parsing to get the content for page.
    List<GameSales> out = mapper.treeToValue(response.get("content"),
        new TypeReference<List<GameSales>>() {
        });

    // Check has 1 element match
    MatcherAssert.assertThat(out, Matchers.contains(gs1));
    MatcherAssert.assertThat(response.get("totalElements").intValue(), Matchers.equalTo(1));
  }

  @Test
  void findGameSalesBySalePriceGreaterThan() throws Exception {
    MvcResult mvcResult = client.perform(get("/game-sales/salePrice")
        .param("gt", "50.0")
    ).andExpect(status().isOk()).andReturn();

    // Map the response to JsonNode for the ease of getting the content(s).
    JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsString());

    // parsing to get the content for page.
    List<GameSales> out = mapper.treeToValue(response.get("content"),
        new TypeReference<List<GameSales>>() {
        });

    // Check has 2 elements match
    MatcherAssert.assertThat(out, Matchers.containsInAnyOrder(gs1, gs2));
    MatcherAssert.assertThat(response.get("totalElements").intValue(), Matchers.equalTo(2));
  }
}