package com.course_registration.courseRegistration.Service;


import com.course_registration.courseRegistration.Dao.LectureApplyRepository;
import com.course_registration.courseRegistration.domain.Lecture;
import com.course_registration.courseRegistration.domain.LectureApply;
import com.course_registration.courseRegistration.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LectureApplyService {

    private final LectureApplyRepository lectureApplyRepository;

    @Transactional
    public void saveLectureApply(Lecture lecture, Member member){ //수강신청에 대한 인스턴스 생성
        LectureApply lectureApply=LectureApply.createApply(
            lecture.getSubjectNumber(), lecture.getClassNum()
        );

        lectureApply.setLecture(lecture);
        lectureApply.setMember(member);

        member.setCurrentCredits(lecture);
        lecture.setCurrentNum();

        lectureApplyRepository.save(lectureApply);


    }

    @Transactional
    public void doLectureApplyByCheckCondition( Lecture lecture, Member member, List<LectureApply> lectureApplyList,PrintWriter out) {


        if((lecture.getDepartment().equals(member.getDepartment()))&&(lecture.getForGrade()==member.getGrade())) { //강의의 대상과 부합할 경우


            if ((member.getCurrentCredits() + lecture.getCredit() <= 9) && (lecture.getCurrentNum() + 1 <= lecture.getMaxNum())) { //최대학점을 초과하지 않고 정원을 초과하지 않을 경우

                if (lectureApplyList.size() != 0) {
                    for (int i = 0; i < lectureApplyList.size(); i++) {  //그전에 신청한 강의 중 같은 강의가 있다면 신청할 수 없도록 함
                        if (lectureApplyList.get(i).getLecture().getSubject().equals(lecture.getSubject())) {

                            out.println("<script>alert('같은 강의를 중복되어 신청할 수 없습니다.'); history.go(-1); </script>");

                            out.flush();
                            break;

                        }

                        if (i == lectureApplyList.size() - 1) {
                           saveLectureApply(lecture, member);

                            out.println("<script>alert('수강신청이 되었습니다'); history.go(-1); </script>");

                            out.flush();

                            break;
                        }
                    }
                } else {
                    saveLectureApply(lecture, member);

                    out.println("<script>alert('수강신청이 되었습니다'); history.go(-1); </script>");

                    out.flush();
                }
            } else {
                if (member.getCurrentCredits() + lecture.getCredit() > 9) {

                    out.println("<script>alert('최대 9학점을 넘을 수 없습니다.'); history.go(-1); </script>");

                    out.flush();
                }
                if (lecture.getCurrentNum() + 1 > lecture.getMaxNum()) {


                    out.println("<script>alert('정원이 초과되어 신청할 수 없습니다.'); history.go(-1); </script>");

                    out.flush();
                }
            }

        }
        else{  //강의 대상에 부합하지 않을 경우
            out.println("<script>alert('학과와 대상 학년을 확인해주시기 바랍니다.'); history.go(-1); </script>");

            out.flush();
        }
    }



}
