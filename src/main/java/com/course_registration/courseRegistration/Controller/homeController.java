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

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class homeController {

    private final MemberService memberService;
    private final LectureService lectureService;

    @GetMapping("/")
    public String showIndexPage(Model model, Principal principal)  {


        if(principal!=null) {

            String loginId=principal.getName();

            Member member=memberService.findByLoginId(loginId);

            List<Lecture> lectureList=lectureService.getAllLectures();

            model.addAttribute("nickName",member.getNickName());  //index 페이지로 현재 로그인한 학생의 이름 전달
            model.addAttribute("loginId",member.getLoginId()); //index 페이지로 현재 로그인한 학생의 학번 전달
            model.addAttribute("lectureList",lectureList);

        }
        return "index";

    }
}
