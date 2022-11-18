package com.course_registration.courseRegistration.Dao;

import com.course_registration.courseRegistration.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture,Long> {
}
