package org.airtribe.LearnerManagementSystem.dto;

import java.util.List;


public class LearnerList {

  private List<Long> learnerIds;

  public LearnerList(List<Long> learnerIds) {
    this.learnerIds = learnerIds;
  }

  public List<Long> getLearnerIds() {
    return learnerIds;
  }

  public void setLearnerIds(List<Long> learnerIds) {
    this.learnerIds = learnerIds;
  }
}
