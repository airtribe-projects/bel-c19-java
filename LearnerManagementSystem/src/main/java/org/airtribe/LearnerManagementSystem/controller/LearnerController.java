package org.airtribe.LearnerManagementSystem.controller;

import java.util.Date;
import java.util.List;
import org.airtribe.LearnerManagementSystem.dto.LearnerDTO;
import org.airtribe.LearnerManagementSystem.entity.ErrorResponse;
import org.airtribe.LearnerManagementSystem.entity.Learner;
import org.airtribe.LearnerManagementSystem.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystem.service.LearnerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LearnerController {

  // endpoint to create a learner

  @Autowired
  private LearnerManagementService _learnerManagementService;

  // {"learnerName":}
  @PostMapping("/learners")
  public Learner createLearner(@RequestBody Learner learner) {
    return _learnerManagementService.createLearner(learner);
  }

//  @GetMapping("/learners")
//  public List<Learner> getAllLearners() {
//    return _learnerManagementService.getAllLearners();
//  }

  @GetMapping("/learners/{learnerId}")
  public Learner getLearnerById(@PathVariable("learnerId") Long learnerId) throws LearnerNotFoundException {
    return _learnerManagementService.findById(learnerId);

  }

//  @GetMapping("/learners/{learnerName}")
//  public Learner getLearnerByName(@PathVariable("learnerName") String learnerName) {
//    return _learnerManagementService.findByLearnerName(learnerName).get(0);
//  }

  @GetMapping("/learners")
  public List<LearnerDTO> fetchLearnersByName(@RequestParam(value = "learnerName", required = false) String learnerName,
      @RequestParam(value = "learnerEmail", required = false) String learnerEmail) {
    List<Learner> learners =  _learnerManagementService.executeBusinessLogic(learnerName, learnerEmail);
    return _learnerManagementService.convertLearnersToLearnerDTOs(learners);
  }

  @PutMapping("/learners/{learnerId}")
  public Learner updateLearner(@PathVariable("learnerId") Long learnerId, @RequestBody Learner learner)
      throws LearnerNotFoundException {
    return _learnerManagementService.updateLearner(learnerId, learner);
  }

  @DeleteMapping("/learners/{learnerId}")
  public ResponseEntity<Void> deleteLearner(@PathVariable("learnerId") Long learnerId) throws LearnerNotFoundException {
    _learnerManagementService.deleteLearner(learnerId);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(LearnerNotFoundException.class)
  public ErrorResponse handleLearnerNotFoundException(LearnerNotFoundException ex) {
    return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), new Date().toInstant().toEpochMilli(), ex.getStackTrace().toString());
  }

}


// /learners?learnerName=John
// /learners?learnerEmail=test
// /learners
// /learners?learnerEmail=test&learnerName=John