package com.example.vgcodetest.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql({"/schema-H2.sql", "/data-H2.sql"})
class ImportControllerTest {

  @Autowired
  private MockMvc client;

  @Autowired
  private ResourceLoader resourceLoader;
  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void importCsv() throws Exception {
    File file = resourceLoader.getResource("classpath:gameTitlesOneK.csv").getFile();

    MockMultipartFile multipartFile = new MockMultipartFile("csv",
        file.getName(), null, new FileInputStream((file)));

    MvcResult mvcResult = client.perform(
        multipart("/import").file(multipartFile)).andExpect(status().isCreated()).andReturn();

    Map<String, String> out = mapper.readValue(mvcResult.getResponse().getContentAsString(),
        new TypeReference<Map<String, String>>() {
        });

    MatcherAssert.assertThat(out.get("status"), Matchers.equalToIgnoringCase("success"));
  }

  @Test
  void importCsvInvalidRecord() throws Exception {
    File file = resourceLoader.getResource("classpath:gameTitlesInvalid.csv").getFile();

    MockMultipartFile multipartFile = new MockMultipartFile("csv",
        file.getName(), null, new FileInputStream((file)));

    MvcResult mvcResult = client.perform(
        multipart("/import").file(multipartFile)).andExpect(status().isBadRequest()).andReturn();

    Map<String, Object> out = mapper.readValue(mvcResult.getResponse().getContentAsString(),
        new TypeReference<Map<String, Object>>() {
        });

    MatcherAssert.assertThat(out.get("statusCode"), Matchers.equalTo(400));
  }
}