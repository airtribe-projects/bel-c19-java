package org.airtribe.LearnerManagementSystem.controller;

import java.util.ArrayList;
import java.util.List;
import org.airtribe.LearnerManagementSystem.dto.LearnerDTO;
import org.airtribe.LearnerManagementSystem.entity.Learner;
import org.airtribe.LearnerManagementSystem.service.LearnerManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class LearnerControllerTest {

  @MockitoBean
  private LearnerManagementService _learnerManagementService;

  @Autowired
  private MockMvc _mockMvc;

  private LearnerController _learnerController;



  @Test
  public void testCreateLearner_successfully() throws Exception {
    // ARRANGE
    Learner learner = new Learner("test", "test@gmail.com", "1234");
    when(_learnerManagementService.createLearner(any())).thenReturn(learner);


    // ACT
    _mockMvc.perform(MockMvcRequestBuilders.post("/learners")
        .contentType("application/json")
        .content("{\"learnerName\":\"pawan2\",\"learnerEmail\":\"test@gmail.com\",\"learnerPhone\":\"1234\"}"))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.learnerName").value("pawan2"));

    // ASSERT
  }

  @Test
  public void testFetchLearnerSuccessfully() throws Exception {
    List<Learner> learnerList = new ArrayList<>();
    Learner learner = new Learner("test", "test@gmail.com", "1234");
    learnerList.add(learner);
    LearnerDTO learnerDTO = new LearnerDTO("test", "test@gmail.com", "1234");
    when(_learnerManagementService.executeBusinessLogic("test", "test@gmail.com")).thenReturn(learnerList);
    when(_learnerManagementService.convertLearnersToLearnerDTOs(learnerList)).thenReturn(List.of(learnerDTO));

    _mockMvc.perform(MockMvcRequestBuilders.get("/learners?learnerName=test&learnerEmail=test@gmail.com")
            .requestAttr("learnerName", "test")
            .requestAttr("learnerEmail", "test@gmail.com")
            .contentType("application/json"))
        .andExpect(status().isOk())
        .andDo(print());
  }

}
