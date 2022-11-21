package com.course_registration.courseRegistration.Controller;

import com.course_registration.courseRegistration.DTO.Member.MemberLoginForm;
import com.course_registration.courseRegistration.Service.LectureService;
import com.course_registration.courseRegistration.Service.MemberService;
import com.course_registration.courseRegistration.domain.Lecture;
import com.course_registration.courseRegistration.domain.LectureApply;
import com.course_registration.courseRegistration.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class memberController {

    private final MemberService memberService;
    private final LectureService lectureService;

    @GetMapping("/members/login")
    public String showLogin(Model model){
        model.addAttribute("memberLoginForm", new MemberLoginForm()); //로그인 폼을 login 페이지로 전달

        return "login";

    }

    @GetMapping("/members/applyHistory")
    public String showMyPage(Model model, Principal principal){  //수강신청 내역 페이지 확인

        Member member=memberService.findByLoginId(principal.getName());

        List<Lecture> lectureList=lectureService.findLectureListByMember(member);

        model.addAttribute("nickName",member.getNickName());
        model.addAttribute("loginId",member.getLoginId());
        model.addAttribute("lectureList",lectureList);

        return "applyHistory";


    }
}
