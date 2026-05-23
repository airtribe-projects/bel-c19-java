package org.airtribe.LearnerManagementSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.plaf.OptionPaneUI;
import org.airtribe.LearnerManagementSystem.dto.CohortDTO;
import org.airtribe.LearnerManagementSystem.dto.LearnerDTO;
import org.airtribe.LearnerManagementSystem.entity.Cohort;
import org.airtribe.LearnerManagementSystem.entity.Course;
import org.airtribe.LearnerManagementSystem.entity.Learner;
import org.airtribe.LearnerManagementSystem.exception.CohortNotFoundException;
import org.airtribe.LearnerManagementSystem.exception.CourseNotFoundException;
import org.airtribe.LearnerManagementSystem.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystem.repository.CohortRepository;
import org.airtribe.LearnerManagementSystem.repository.CourseRepository;
import org.airtribe.LearnerManagementSystem.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class LearnerManagementService {
  @Autowired
  private LearnerRepository _learnerRepository;

  @Autowired
  private CohortRepository _cohortRepository;

  @Autowired
  private CourseRepository _courseRepository;

  public Learner createLearner(Learner learner) {
    return _learnerRepository.save(learner);
  }

  public List<Learner> getAllLearners() {
    return _learnerRepository.findAll();
  }

  public Learner findById(Long learnerId) throws LearnerNotFoundException {
    Optional<Learner> learnerOptional = _learnerRepository.findById(learnerId);
    if (learnerOptional.isEmpty()) {
      throw new LearnerNotFoundException("learner with id " + learnerId + " not found");
    }
    // Try catch
    // throws
    return _learnerRepository.findById(learnerId).get();
  }

  public List<Learner> findByLearnerName(String learnerName) {
    return _learnerRepository.findByLearnerName(learnerName);
  }

  public List<Learner> findByLearnerEmail(String learnerEmail) {
    return _learnerRepository.findByLearnerEmail(learnerEmail);
  }

  public List<Learner> findByLearnerNameAndLearnerEmail(String learnerName, String learnerEmail) {
    return _learnerRepository.findByLearnerNameAndLearnerEmail(learnerName, learnerEmail);
  }

  public List<Learner> executeBusinessLogic(String learnerName, String learnerEmail) {
    if (learnerName != null && learnerEmail != null) {
      return findByLearnerNameAndLearnerEmail(learnerName, learnerEmail);
    }
    if (learnerName == null && learnerEmail==null) {
      return getAllLearners();
    }
    if (learnerName!=null) {
      return findByLearnerName(learnerName);
    }
    return findByLearnerEmail(learnerEmail);
  }

  public Cohort createCohort(Cohort cohort) {
    return _cohortRepository.save(cohort);
  }

  public Cohort assignLearnerToCohort(Long learnerId, Long cohortId)
      throws CohortNotFoundException, LearnerNotFoundException {
    Optional<Cohort> cohortOptional = _cohortRepository.findById(cohortId);
    if (cohortOptional.isEmpty()) {
      throw new CohortNotFoundException("Cohort not found with the id " + cohortId);
    }
    Optional<Learner> learnerOptional = _learnerRepository.findById(learnerId);
    if (learnerOptional.isEmpty()) {
      throw new LearnerNotFoundException("Learner not found with the id " + learnerId);
    }

    Cohort existingCohort = cohortOptional.get();
    for (Learner learner : existingCohort.getLearners()) {
      if (learner.getLearnerId() == learnerId) {
        return existingCohort;
      }
    }

    existingCohort.getLearners().add(learnerOptional.get());
    return _cohortRepository.save(existingCohort);
  }

  public List<Cohort> getAllCohorts() {
    return _cohortRepository.findAll();
  }

  public List<LearnerDTO> convertLearnersToLearnerDTOs(List<Learner> learners) {
    List<LearnerDTO> learnerDTOS = new ArrayList<>();
    for (Learner learner : learners) {
      LearnerDTO learnerDTO = new LearnerDTO();
      learnerDTO.setLearnerId(learner.getLearnerId());
      learnerDTO.setLearnerEmail(learner.getLearnerEmail());
      learnerDTO.setLearnerPhone(learner.getLearnerPhone());
      learnerDTO.setLearnerName(learner.getLearnerName());
      List<CohortDTO> cohortDTOS = new ArrayList<>();
      for (Cohort cohort : learner.getCohorts()) {
        CohortDTO cohortDTO = new CohortDTO();
        cohortDTO.setCohortId(cohort.getCohortId());
        cohortDTO.setCohortName(cohort.getCohortName());
        cohortDTO.setCohortDescription(cohort.getCohortDescription());

        cohortDTOS.add(cohortDTO);
      }

      learnerDTO.setCohortDTO(cohortDTOS);
      learnerDTOS.add(learnerDTO);
    }

    return learnerDTOS;

  }

  public Course createCourse(Course course) {
    return _courseRepository.save(course);
  }

  public Cohort mapLearnersToCohort(Long cohortId, List<Long> learnerIds) throws CohortNotFoundException {
    Optional<Cohort> cohortOptional = _cohortRepository.findById(cohortId);
    if (cohortOptional.isEmpty()) {
      throw new CohortNotFoundException("Cohort not found with the Id " + cohortId);
    }

    Cohort cohortObj = cohortOptional.get();
    for (Long learnerId : learnerIds) {
      Optional<Learner> learnerOptional = _learnerRepository.findById(learnerId);
      if (learnerOptional.isPresent()) {
        Learner learnerObj = learnerOptional.get();
        boolean learnerMapped = false;
        for (Learner mappedLearners : cohortObj.getLearners()) {
          if (mappedLearners.getLearnerId().equals(learnerObj.getLearnerId())) {
            learnerMapped = true;
            break;
          }
        }
        if (!learnerMapped) {
          cohortObj.getLearners().add(learnerObj);
        }
      }
    }
    return _cohortRepository.save(cohortObj);
  }

  // ---------- Learner: update/delete ----------

  public Learner updateLearner(Long learnerId, Learner updated) throws LearnerNotFoundException {
    Learner existing = _learnerRepository.findById(learnerId)
        .orElseThrow(() -> new LearnerNotFoundException("Learner not found with id " + learnerId));
    if (updated.getLearnerName() != null) existing.setLearnerName(updated.getLearnerName());
    if (updated.getLearnerEmail() != null) existing.setLearnerEmail(updated.getLearnerEmail());
    if (updated.getLearnerPhone() != null) existing.setLearnerPhone(updated.getLearnerPhone());
    return _learnerRepository.save(existing);
  }

  public void deleteLearner(Long learnerId) throws LearnerNotFoundException {
    if (!_learnerRepository.existsById(learnerId)) {
      throw new LearnerNotFoundException("Learner not found with id " + learnerId);
    }
    _learnerRepository.deleteById(learnerId);
  }

  // ---------- Course: CRUD ----------

  public List<Course> getAllCourses() {
    return _courseRepository.findAll();
  }

  public Course getCourseById(Long courseId) throws CourseNotFoundException {
    return _courseRepository.findById(courseId)
        .orElseThrow(() -> new CourseNotFoundException("Course not found with id " + courseId));
  }

  public Course updateCourse(Long courseId, Course updated) throws CourseNotFoundException {
    Course existing = getCourseById(courseId);
    if (updated.getCourseName() != null) existing.setCourseName(updated.getCourseName());
    if (updated.getCourseDescription() != null) existing.setCourseDescription(updated.getCourseDescription());
    return _courseRepository.save(existing);
  }

  public void deleteCourse(Long courseId) throws CourseNotFoundException {
    if (!_courseRepository.existsById(courseId)) {
      throw new CourseNotFoundException("Course not found with id " + courseId);
    }
    _courseRepository.deleteById(courseId);
  }

  // ---------- Cohort: nested-under-course + extras ----------

  public Cohort createCohortUnderCourse(Long courseId, Cohort cohort) throws CourseNotFoundException {
    Course parentCourse = getCourseById(courseId);
    cohort.setCourse(parentCourse);
    return _cohortRepository.save(cohort);
  }

  public List<Cohort> getCohortsForCourse(Long courseId) throws CourseNotFoundException {
    Course parentCourse = getCourseById(courseId);
    return parentCourse.getCohorts();
  }

  public Cohort getCohortById(Long cohortId) throws CohortNotFoundException {
    return _cohortRepository.findById(cohortId)
        .orElseThrow(() -> new CohortNotFoundException("Cohort not found with id " + cohortId));
  }

  public Cohort getCohortUnderCourse(Long courseId, Long cohortId)
      throws CourseNotFoundException, CohortNotFoundException {
    Cohort cohort = getCohortById(cohortId);
    if (cohort.getCourse() == null || !cohort.getCourse().getCourseId().equals(courseId)) {
      throw new CohortNotFoundException(
          "Cohort " + cohortId + " does not belong to course " + courseId);
    }
    return cohort;
  }

  public Cohort updateCohort(Long courseId, Long cohortId, Cohort updated)
      throws CourseNotFoundException, CohortNotFoundException {
    Cohort existing = getCohortUnderCourse(courseId, cohortId);
    if (updated.getCohortName() != null) existing.setCohortName(updated.getCohortName());
    if (updated.getCohortDescription() != null) existing.setCohortDescription(updated.getCohortDescription());
    return _cohortRepository.save(existing);
  }

  public void deleteCohort(Long courseId, Long cohortId)
      throws CourseNotFoundException, CohortNotFoundException {
    Cohort existing = getCohortUnderCourse(courseId, cohortId);
    _cohortRepository.delete(existing);
  }

  public List<Learner> getLearnersInCohort(Long cohortId) throws CohortNotFoundException {
    return getCohortById(cohortId).getLearners();
  }

  public Cohort removeLearnerFromCohort(Long cohortId, Long learnerId)
      throws CohortNotFoundException, LearnerNotFoundException {
    Cohort cohort = getCohortById(cohortId);
    if (!_learnerRepository.existsById(learnerId)) {
      throw new LearnerNotFoundException("Learner not found with id " + learnerId);
    }
    cohort.getLearners().removeIf(l -> l.getLearnerId().equals(learnerId));
    return _cohortRepository.save(cohort);
  }
}

// service -> database -> save(learner)
// CREATE LEARNER