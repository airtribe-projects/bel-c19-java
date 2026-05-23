package org.airtribe.LearnerManagementSystem.dto;

import java.util.List;


public class LearnerDTO {
  private Long learnerId;

  private String learnerName;

  private String learnerEmail;

  private String learnerPhone;

  private List<CohortDTO> cohortDTO;

  public LearnerDTO(Long learnerId, String learnerName, String learnerEmail, String learnerPhone,
      List<CohortDTO> cohortDTO) {
    this.learnerId = learnerId;
    this.learnerName = learnerName;
    this.learnerEmail = learnerEmail;
    this.learnerPhone = learnerPhone;
    this.cohortDTO = cohortDTO;
  }

  public LearnerDTO() {

  }

  public Long getLearnerId() {
    return learnerId;
  }

  public void setLearnerId(Long learnerId) {
    this.learnerId = learnerId;
  }

  public String getLearnerName() {
    return learnerName;
  }

  public void setLearnerName(String learnerName) {
    this.learnerName = learnerName;
  }

  public String getLearnerEmail() {
    return learnerEmail;
  }

  public void setLearnerEmail(String learnerEmail) {
    this.learnerEmail = learnerEmail;
  }

  public String getLearnerPhone() {
    return learnerPhone;
  }

  public void setLearnerPhone(String learnerPhone) {
    this.learnerPhone = learnerPhone;
  }

  public List<CohortDTO> getCohortDTO() {
    return cohortDTO;
  }

  public void setCohortDTO(List<CohortDTO> cohortDTO) {
    this.cohortDTO = cohortDTO;
  }
}
