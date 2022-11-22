package com.course_registration.courseRegistration.Controller;

import com.course_registration.courseRegistration.DTO.Member.MemberLoginForm;
import com.course_registration.courseRegistration.Service.LectureService;
import com.course_registration.courseRegistration.Service.MemberService;
import com.course_registration.courseRegistration.domain.Lecture;
import com.course_registration.courseRegistration.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class homeController {

    private final MemberService memberService;
    private final LectureService lectureService;

    @GetMapping("/")
        public String showIndexPage(Model model, Principal principal,@RequestParam(name="department",defaultValue ="") String department,@RequestParam(name="forGrade",defaultValue ="")String forGrade,@RequestParam(name="professorName",defaultValue ="")String professorName,@RequestParam(name="subject",defaultValue ="")String subject,@RequestParam(name="subjectNumber",defaultValue ="")String subjectNumber)  {


        if(principal!=null) {   //로그인한 상태라면



            Member admin=memberService.findAdminMember();

            LocalDateTime now = LocalDateTime.now();

            if(admin.getCanApplyStartTime()!=null&&admin.getCanApplyEndTime()!=null&&(!principal.getName().equals(admin.getLoginId()))){ //관리자가 아니고 이전에 관리자가 올바른 시작시간과 종료시간을 설정할 경우
                LocalDateTime startTime=admin.getCanApplyStartTime();
                LocalDateTime endTime=admin.getCanApplyEndTime();

                if(now.isAfter(startTime)&&now.isBefore(endTime)){  //현재시간이 시작시간보다는 이후 시간이고 종료시간보다는 이전 시간일 경우(수강신청 기간일 경우)
                    String loginId=principal.getName();

                    Member member=memberService.findByLoginId(loginId);

                    List<Lecture> lectureList=lectureService.findListBySearchKeyword(department,forGrade,professorName,subject,subjectNumber);

                    model.addAttribute("nickName",member.getNickName());  //index 페이지로 현재 로그인한 학생의 이름 전달
                    model.addAttribute("loginId",member.getLoginId()); //index 페이지로 현재 로그인한 학생의 학번 전달
                    model.addAttribute("lectureList",lectureList);

                    return "index";
                }
                else{  // 수강신청 기간이 아닐 경우

                    Member member=memberService.findByLoginId(principal.getName());

                    String convertedDate1 = startTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));
                    String convertedDate2 = endTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss"));

                    String notification="("+convertedDate1+"부터 "+ convertedDate2+"까지 수강신청이 가능합니다.)";

                    model.addAttribute("nickName",member.getNickName()); //수강신청 불가 페이지로 현재 로그인한 학생 이름 전달
                    model.addAttribute("loginId",member.getLoginId());  //수강신청 불가 페이지로 현재 로그인한 학생 학번 전달
                    model.addAttribute("notification",notification);

                    return "nonAllowed";
                }

            }
            else{
                String loginId=principal.getName();

                Member member=memberService.findByLoginId(loginId);

                List<Lecture> lectureList=lectureService.findListBySearchKeyword(department,forGrade,professorName,subject,subjectNumber);

                model.addAttribute("nickName",member.getNickName());  //index 페이지로 현재 로그인한 학생의 이름 전달
                model.addAttribute("loginId",member.getLoginId()); //index 페이지로 현재 로그인한 학생의 학번 전달
                model.addAttribute("lectureList",lectureList);

                return "index";
            }


        }

        return "index";
    }
}
