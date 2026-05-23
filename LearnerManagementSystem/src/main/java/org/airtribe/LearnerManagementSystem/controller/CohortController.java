package org.airtribe.LearnerManagementSystem.controller;

import java.util.Date;
import java.util.List;
import org.airtribe.LearnerManagementSystem.dto.LearnerList;
import org.airtribe.LearnerManagementSystem.entity.Cohort;
import org.airtribe.LearnerManagementSystem.entity.ErrorResponse;
import org.airtribe.LearnerManagementSystem.entity.Learner;
import org.airtribe.LearnerManagementSystem.exception.CohortNotFoundException;
import org.airtribe.LearnerManagementSystem.exception.CourseNotFoundException;
import org.airtribe.LearnerManagementSystem.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystem.service.LearnerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class CohortController {
  @Autowired
  private LearnerManagementService _learnerManagementService;

  // ----- Existing flat endpoints (kept for backwards compatibility) -----

  @PostMapping("/cohorts")
  public Cohort createCohort(@RequestBody Cohort cohort) {
    return _learnerManagementService.createCohort(cohort);
  }

  @PostMapping("/assignLearnerToCohort")
  public Cohort assignLearnerToCohort(@RequestParam("learnerId") Long learnerId, @RequestParam("cohortId") Long cohortId)
      throws CohortNotFoundException, LearnerNotFoundException {
    return _learnerManagementService.assignLearnerToCohort(learnerId, cohortId);
  }

  @PostMapping("/cohorts/{cohortId}/learners")
  public Cohort mapLearnersToCohorts(@PathVariable("cohortId") Long cohortId, @RequestBody LearnerList learnerList)
      throws CohortNotFoundException {
    return _learnerManagementService.mapLearnersToCohort(cohortId, learnerList.getLearnerIds());
  }

  @GetMapping("/cohorts")
  public List<Cohort> getAllCohorts() {
    return _learnerManagementService.getAllCohorts();
  }

  // ----- Cohort by id + learners sub-resource -----

  @GetMapping("/cohorts/{cohortId}")
  public Cohort getCohortById(@PathVariable("cohortId") Long cohortId) throws CohortNotFoundException {
    return _learnerManagementService.getCohortById(cohortId);
  }

  @GetMapping("/cohorts/{cohortId}/learners")
  public List<Learner> getLearnersInCohort(@PathVariable("cohortId") Long cohortId) throws CohortNotFoundException {
    return _learnerManagementService.getLearnersInCohort(cohortId);
  }

  @DeleteMapping("/cohorts/{cohortId}/learners/{learnerId}")
  public Cohort removeLearnerFromCohort(@PathVariable("cohortId") Long cohortId,
      @PathVariable("learnerId") Long learnerId) throws CohortNotFoundException, LearnerNotFoundException {
    return _learnerManagementService.removeLearnerFromCohort(cohortId, learnerId);
  }

  // ----- Cohort nested under Course (parent-child) -----

  @PostMapping("/courses/{courseId}/cohorts")
  public Cohort createCohortUnderCourse(@PathVariable("courseId") Long courseId, @RequestBody Cohort cohort)
      throws CourseNotFoundException {
    return _learnerManagementService.createCohortUnderCourse(courseId, cohort);
  }

  @GetMapping("/courses/{courseId}/cohorts")
  public List<Cohort> getCohortsForCourse(@PathVariable("courseId") Long courseId) throws CourseNotFoundException {
    return _learnerManagementService.getCohortsForCourse(courseId);
  }

  @GetMapping("/courses/{courseId}/cohorts/{cohortId}")
  public Cohort getCohortUnderCourse(@PathVariable("courseId") Long courseId,
      @PathVariable("cohortId") Long cohortId) throws CourseNotFoundException, CohortNotFoundException {
    return _learnerManagementService.getCohortUnderCourse(courseId, cohortId);
  }

  @PutMapping("/courses/{courseId}/cohorts/{cohortId}")
  public Cohort updateCohort(@PathVariable("courseId") Long courseId, @PathVariable("cohortId") Long cohortId,
      @RequestBody Cohort cohort) throws CourseNotFoundException, CohortNotFoundException {
    return _learnerManagementService.updateCohort(courseId, cohortId, cohort);
  }

  @DeleteMapping("/courses/{courseId}/cohorts/{cohortId}")
  public ResponseEntity<Void> deleteCohort(@PathVariable("courseId") Long courseId,
      @PathVariable("cohortId") Long cohortId) throws CourseNotFoundException, CohortNotFoundException {
    _learnerManagementService.deleteCohort(courseId, cohortId);
    return ResponseEntity.noContent().build();
  }

  // ----- Exception handlers -----

  @ExceptionHandler(LearnerNotFoundException.class)
  public ErrorResponse handleLearnerNotFoundException(LearnerNotFoundException ex) {
    return new ErrorResponse(
        HttpStatus.NOT_FOUND, ex.getMessage(), new Date().toInstant().toEpochMilli(), ex.getStackTrace().toString());
  }

  @ExceptionHandler(CohortNotFoundException.class)
  public ErrorResponse handleCohortNotFoundException(CohortNotFoundException ex) {
    return new ErrorResponse(
        HttpStatus.NOT_FOUND, ex.getMessage(), new Date().toInstant().toEpochMilli(), ex.getStackTrace().toString());
  }

  @ExceptionHandler(CourseNotFoundException.class)
  public ErrorResponse handleCourseNotFoundException(CourseNotFoundException ex) {
    return new ErrorResponse(
        HttpStatus.NOT_FOUND, ex.getMessage(), new Date().toInstant().toEpochMilli(), ex.getStackTrace().toString());
  }
}
