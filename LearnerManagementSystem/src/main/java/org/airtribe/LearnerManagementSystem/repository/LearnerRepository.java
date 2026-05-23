package org.airtribe.LearnerManagementSystem.repository;

import java.util.List;
import org.airtribe.LearnerManagementSystem.entity.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LearnerRepository extends JpaRepository<Learner, Long> {
  public List<Learner> findByLearnerName(String learnerName);

  @Query("select l.learnerEmail from Learner l where l.learnerName = ?1")
  public Learner searchMeLearner(String learnerName);

  public List<Learner> findByLearnerEmail(String learnerEmail);

  public List<Learner> findByLearnerNameAndLearnerEmail(String learnerName, String learnerEmail);
}
