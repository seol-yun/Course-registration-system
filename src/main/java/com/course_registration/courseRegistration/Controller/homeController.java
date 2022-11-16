package com.course_registration.courseRegistration.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class homeController {

    @GetMapping("/")
    public String showHome(Model model, Principal principal)  {
        return "index";
    }
}
