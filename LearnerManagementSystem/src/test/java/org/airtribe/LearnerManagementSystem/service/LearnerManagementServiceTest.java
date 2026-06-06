package org.airtribe.LearnerManagementSystem.service;

import java.util.Optional;
import org.airtribe.LearnerManagementSystem.entity.Learner;
import org.airtribe.LearnerManagementSystem.exception.CohortNotFoundException;
import org.airtribe.LearnerManagementSystem.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystem.repository.LearnerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LearnerManagementServiceTest {

  @Mock
  private LearnerRepository _learnerRepository;

  @InjectMocks
  private LearnerManagementService _learnerManagementService;

  private Learner learner;


  @BeforeAll
  public static void setupBeforeAll() {

    System.out.println("Running once before all the unit tests");
  }

  @BeforeEach
  public void setupBeforeEach() {
    learner = new Learner(1L, "test", "test", "12345");
    System.out.println("Running once before each unit test");
  }

  @AfterEach
  public void setupAfterEach() {
    System.out.println("Running once after each unit test");
  }

  @AfterAll
  public static void setupAfterAll() {
    System.out.println("Running once after all the unit tests");
  }

  // Happy scenario
  @Test
  public void testCreateLearnerSuccessfully() {
    // ARRANGE

    when(_learnerRepository.save(learner)).thenReturn(learner); // mocking the repository call

    // ACT
    Learner savedLearner = _learnerManagementService.createLearner(learner);

    // ASSERT
    Assertions.assertEquals(1L, savedLearner.getLearnerId());
    Assertions.assertEquals("test", savedLearner.getLearnerName());
    Assertions.assertEquals("test", savedLearner.getLearnerEmail());
    Assertions.assertNotNull(savedLearner);
    verify(_learnerRepository, times(1)).save(any());
  }


  @Test
  public void testFetchLearnerByIDSuccessfully() throws LearnerNotFoundException {
    when(_learnerRepository.findById(1L)).thenReturn(Optional.of(learner));


    //ACT
    Learner expectedLearner = _learnerManagementService.findById(1L);
    Assertions.assertNotNull(expectedLearner);
    Assertions.assertEquals(learner, expectedLearner);
    Assertions.assertEquals(1L, expectedLearner.getLearnerId());
  }

  // Negative scenario
  @Test
  public void testFetchLearnerById_LearnerNotFoundException() {
    when(_learnerRepository.findById(1L)).thenReturn(Optional.empty());

    LearnerNotFoundException exception = Assertions.assertThrows(LearnerNotFoundException.class, () -> {
      _learnerManagementService.findById(1L);
    });
    Assertions.assertEquals("learner with id 1 not found", exception.getMessage());
  }
}
