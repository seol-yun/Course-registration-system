package com.course_registration.courseRegistration.Service;


import com.course_registration.courseRegistration.Dao.LectureApplyRepository;
import com.course_registration.courseRegistration.domain.Lecture;
import com.course_registration.courseRegistration.domain.LectureApply;
import com.course_registration.courseRegistration.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LectureApplyService {

    private final LectureApplyRepository lectureApplyRepository;

    @Transactional
    public void saveLectureApply(Lecture lecture, Member member){
        LectureApply lectureApply=LectureApply.createApply(
            lecture.getSubjectNumber(), lecture.getClassNum()
        );

        lectureApply.setLecture(lecture);
        lectureApply.setMember(member);

        member.setCurrentCredits(lecture);
        lecture.setCurrentNum();

        lectureApplyRepository.save(lectureApply);
    }
}
