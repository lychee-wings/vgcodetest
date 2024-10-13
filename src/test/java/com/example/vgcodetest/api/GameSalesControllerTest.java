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
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

  private GameSales gameSales;

  private ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  void setUp() {

    gameSales = new GameSales(10001L, 90, "Assassin's Creed III", "PS3", 2, 59.0, 0.09, 64.31,
        Timestamp.valueOf("2024-04-01 10:00:00"),
        IngestionHistory.builder().id(1L).fileName("unitTest").status("success").build());
  }

  @AfterEach
  void tearDown() {
  }

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

    MatcherAssert.assertThat(out, Matchers.hasItems(gameSales));

  }
}