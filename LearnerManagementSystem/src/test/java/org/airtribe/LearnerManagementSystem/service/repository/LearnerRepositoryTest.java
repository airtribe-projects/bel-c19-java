package org.airtribe.LearnerManagementSystem.service.repository;

import java.util.List;
import org.airtribe.LearnerManagementSystem.entity.Learner;
import org.airtribe.LearnerManagementSystem.repository.LearnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LearnerRepositoryTest {

  @Autowired
  private LearnerRepository _learnerRepository;


  @Test
  @Rollback(value = false)
  public void testSaveLearnerSuccessfully() {
    // ARRANGE
    Learner learner = new Learner("test", "test", "12345");
    Learner savedLearner = _learnerRepository.save(learner);
    System.out.println(savedLearner.getLearnerId());
    Assertions.assertNotNull(savedLearner.getLearnerId());
    Assertions.assertEquals("test", savedLearner.getLearnerName());
  }

  @Test
  public void testFetchLearners() {
    // ARRANGE
    Learner learner = new Learner("test", "test", "12345");
    _learnerRepository.save(learner);
    List<Learner> learners = _learnerRepository.findAll();
    Assertions.assertEquals(1, learners.size());
  }
}
