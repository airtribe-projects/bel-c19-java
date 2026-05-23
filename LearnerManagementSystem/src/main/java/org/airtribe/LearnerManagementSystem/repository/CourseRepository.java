package org.airtribe.LearnerManagementSystem.repository;

import org.airtribe.LearnerManagementSystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
