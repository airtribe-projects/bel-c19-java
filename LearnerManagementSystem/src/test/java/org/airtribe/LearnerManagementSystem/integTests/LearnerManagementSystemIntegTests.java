package org.airtribe.LearnerManagementSystem.integTests;

import org.airtribe.LearnerManagementSystem.entity.Learner;
import org.airtribe.LearnerManagementSystem.repository.LearnerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class LearnerManagementSystemIntegTests {

  @Autowired
  private MockMvc _mockMvc;

  @Autowired
  private LearnerRepository _learnerRepository;


  @AfterEach
  public void cleanup() {
    _learnerRepository.deleteAll();
  }

  @BeforeEach
  public void cleanupBeforeEach() {
    _learnerRepository.deleteAll();
  }


  @Test
  public void testCreateLearner_successfully() throws Exception {
    // ARRANGE
    Learner learner = new Learner("test", "test@gmail.com", "1234");

    // ACT
    _mockMvc.perform(MockMvcRequestBuilders.post("/learners")
            .contentType("application/json")
            .content("{\"learnerName\":\"test\",\"learnerEmail\":\"test@gmail.com\",\"learnerPhone\":\"1234\"}"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.learnerName").value("test"));

    // ASSERT
  }
}
