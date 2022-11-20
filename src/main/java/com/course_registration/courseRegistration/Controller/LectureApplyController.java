package com.course_registration.courseRegistration.Controller;

import com.course_registration.courseRegistration.Service.LectureApplyService;
import com.course_registration.courseRegistration.Service.LectureService;
import com.course_registration.courseRegistration.Service.MemberService;
import com.course_registration.courseRegistration.domain.Lecture;
import com.course_registration.courseRegistration.domain.LectureApply;
import com.course_registration.courseRegistration.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LectureApplyController {

    private final MemberService memberService;
    private final LectureService lectureService;
    private final LectureApplyService lectureApplyService;


    @PostMapping("/members/applyLecture/{lectureId}")   //수강 신청
    public String doLectureApply(@PathVariable(name="lectureId")Long lectureId, Model model, Principal principal, HttpServletResponse response){

        try{
            Member member=memberService.findByLoginId(principal.getName());

            Lecture lecture=lectureService.getLecture(lectureId);

            List<LectureApply> lectureApplyList=memberService.LectureApplyListByMember(member);

            response.setContentType("text/html; charset=euc-kr");

            PrintWriter out = response.getWriter();

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
                                lectureApplyService.saveLectureApply(lecture, member);
                                break;
                            }
                        }
                    } else {
                        lectureApplyService.saveLectureApply(lecture, member);
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
        catch(Exception e){ //수강 신청 시에 오류가 발생하면 해당 오류를 확인할 수 있도록 하고 다시 수강신청 하기 전 페이지로 돌아가도록 함
            model.addAttribute("error_msg",e.getMessage());
            return "index";
        }
        return "redirect:/";
    }
}
