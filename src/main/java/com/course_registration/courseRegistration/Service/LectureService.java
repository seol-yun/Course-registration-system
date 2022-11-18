package com.course_registration.courseRegistration.Service;


import com.course_registration.courseRegistration.Dao.LectureRepository;
import com.course_registration.courseRegistration.domain.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LectureService {

    private final LectureRepository lectureRepository;

    public List<Lecture> getAllLectures(){
        List<Lecture> lectureList=lectureRepository.findAll();

        return lectureList;
    }
}
