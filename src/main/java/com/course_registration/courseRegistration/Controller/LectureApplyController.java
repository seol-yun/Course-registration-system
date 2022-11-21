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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LectureApplyController {

    private final MemberService memberService;
    private final LectureService lectureService;
    private final LectureApplyService lectureApplyService;


    @PostMapping("/members/applyLecture/{lectureId}")   //신청 버튼으로 수강 신청
    public void doLectureApply(@PathVariable(name = "lectureId") Long lectureId, Principal principal, HttpServletResponse response) throws Exception {

            Member member = memberService.findByLoginId(principal.getName());

            List<LectureApply> lectureApplyList = memberService.LectureApplyListByMember(member);

            Lecture lecture = lectureService.getLecture(lectureId);

            response.setContentType("text/html; charset=euc-kr");

            PrintWriter out = response.getWriter();

            lectureApplyService.doLectureApplyByCheckCondition(lecture, member, lectureApplyList,out);




    }

    @PostMapping("/members/applyBySubjectNum") //과목번호로 수강신청
    public void doApplyBySubjectNum(Principal principal, @RequestParam(name = "subjectNumber", defaultValue = "") String subjectNumber, @RequestParam(name = "classNum", defaultValue = "") String classNum, HttpServletResponse response) throws Exception{

            Member member = memberService.findByLoginId(principal.getName());

            List<LectureApply> lectureApplyList = memberService.LectureApplyListByMember(member);

            response.setContentType("text/html; charset=euc-kr");

            PrintWriter out = response.getWriter();

            if ((!subjectNumber.equals("")) && (!classNum.equals(""))) {
                Lecture lecture = lectureService.findBySubjectNumAndClassNum(subjectNumber, classNum);


                if(lecture!=null){
                    lectureApplyService.doLectureApplyByCheckCondition(lecture, member, lectureApplyList,out);
                }
                else{
                    out.println("<script>alert('과목번호와 분반을 정확하게 입력해주시기 바랍니다.'); history.go(-1); </script>");

                    out.flush();
                }
            }
            else{
                out.println("<script>alert('과목번호와 분반을 정확하게 입력해주시기 바랍니다.'); history.go(-1); </script>");

                out.flush();
            }
    }

    @PostMapping("/members/applyHistory/cancel/{lectureId}")
    public String doCancelLectureApply(Model model,Principal principal,@PathVariable(name="lectureId")Long lectureId){

        Lecture lecture=lectureService.getLecture(lectureId);

        Member member=memberService.findByLoginId(principal.getName());

        lectureApplyService.deleteApplyLecture(lecture,member);

        List<Lecture> lectureList=lectureService.findLectureListByMember(member);

        model.addAttribute("nickName",member.getNickName());
        model.addAttribute("loginId",member.getLoginId());
        model.addAttribute("lectureList",lectureList);

        return "applyHistory";
    }



}
