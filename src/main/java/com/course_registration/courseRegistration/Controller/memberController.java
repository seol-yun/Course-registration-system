package com.course_registration.courseRegistration.Controller;

import com.course_registration.courseRegistration.DTO.Member.MemberLoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class memberController {

    @GetMapping("/members/login")
    public String showLogin(Model model){
        model.addAttribute("memberLoginForm", new MemberLoginForm()); //로그인 폼을 login 페이지로 전달

        return "login";

    }
}
