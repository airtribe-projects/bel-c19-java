package org.airtribe.LearnerManagementSystem.controller;

import java.util.Date;
import java.util.List;
import org.airtribe.LearnerManagementSystem.entity.Course;
import org.airtribe.LearnerManagementSystem.entity.ErrorResponse;
import org.airtribe.LearnerManagementSystem.exception.CourseNotFoundException;
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
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CourseController {

  @Autowired
  private LearnerManagementService _learnerManagementService;

  @PostMapping("/courses")
  public Course createCourse(@RequestBody Course course) {
    return _learnerManagementService.createCourse(course);
  }

  @GetMapping("/courses")
  public List<Course> getAllCourses() {
    return _learnerManagementService.getAllCourses();
  }

  @GetMapping("/courses/{courseId}")
  public Course getCourseById(@PathVariable("courseId") Long courseId) throws CourseNotFoundException {
    return _learnerManagementService.getCourseById(courseId);
  }

  @PutMapping("/courses/{courseId}")
  public Course updateCourse(@PathVariable("courseId") Long courseId, @RequestBody Course course)
      throws CourseNotFoundException {
    return _learnerManagementService.updateCourse(courseId, course);
  }

  @DeleteMapping("/courses/{courseId}")
  public ResponseEntity<Void> deleteCourse(@PathVariable("courseId") Long courseId) throws CourseNotFoundException {
    _learnerManagementService.deleteCourse(courseId);
    return ResponseEntity.noContent().build();
  }

  @ExceptionHandler(CourseNotFoundException.class)
  public ErrorResponse handleCourseNotFoundException(CourseNotFoundException ex) {
    return new ErrorResponse(
        HttpStatus.NOT_FOUND, ex.getMessage(), new Date().toInstant().toEpochMilli(), ex.getStackTrace().toString());
  }
}
