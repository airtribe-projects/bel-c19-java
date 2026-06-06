package org.airtribe.LearnerManagementSystem.service.repository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.stream.Stream;
import org.airtribe.LearnerManagementSystem.entity.Learner;
import org.airtribe.LearnerManagementSystem.repository.LearnerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LearnerRepositoryValidationTest {

  @Autowired
  private LearnerRepository _learnerRepository;


  /**
   * Supplies invalid {@link Learner} cases that must violate Bean Validation on flush.
   * Each case provides the three constructor field values plus the property path that is
   * expected to be violated and a fragment of the expected violation message.
   * Note: validation only fires on flush, so {@code saveAndFlush} is used in the test body.
   */
  private static Stream<Arguments> invalidLearners() {
    return Stream.of(
        Arguments.of("name=null", null, "alice@example.com", "12345", "learnerName", "must not be null"),
        Arguments.of("name=empty", "", "alice@example.com", "12345", "learnerName", "must not be empty"),
        Arguments.of("email=not-an-email", "Alice", "not-an-email", "12345", "learnerEmail",
            "must be a well-formed email address"),
        Arguments.of("phone=null", "Alice", "alice@example.com", null, "learnerPhone", "must not be null"),
        Arguments.of("phone=empty", "Alice", "alice@example.com", "", "learnerPhone", "must not be empty"),
        Arguments.of("phone=abc", "Alice", "alice@example.com", "abc", "learnerPhone", "must be greater than 0"),
        Arguments.of("phone=-5", "Alice", "alice@example.com", "-5", "learnerPhone", "must be greater than 0"),
        Arguments.of("phone=0", "Alice", "alice@example.com", "0", "learnerPhone", "must be greater than 0")
    );
  }

  @ParameterizedTest(name = "saveAndFlush [{0}] -> violation on {4} containing \"{5}\"")
  @MethodSource("invalidLearners")
  public void saveAndFlush_invalidLearner_throwsConstraintViolation(
      String caseName, String learnerName, String learnerEmail, String learnerPhone,
      String expectedProperty, String expectedMessageFragment) {
    // ARRANGE
    Learner learner = new Learner(learnerName, learnerEmail, learnerPhone);

    // ACT
    ConstraintViolationException exception = Assertions.assertThrows(
        ConstraintViolationException.class,
        () -> _learnerRepository.saveAndFlush(learner));

    // ASSERT
    boolean hasExpectedViolation = exception.getConstraintViolations().stream()
        .anyMatch(violation -> isExpectedViolation(violation, expectedProperty, expectedMessageFragment));
    Assertions.assertTrue(hasExpectedViolation,
        "Expected a violation on '" + expectedProperty + "' containing \"" + expectedMessageFragment
            + "\" but got: " + exception.getConstraintViolations());
  }

  private static boolean isExpectedViolation(
      ConstraintViolation<?> violation, String expectedProperty, String expectedMessageFragment) {
    return violation.getPropertyPath().toString().equals(expectedProperty)
        && violation.getMessage().contains(expectedMessageFragment);
  }
}
