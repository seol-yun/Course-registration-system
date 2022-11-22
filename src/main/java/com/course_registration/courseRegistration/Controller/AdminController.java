package com.course_registration.courseRegistration.Controller;

import com.course_registration.courseRegistration.DTO.Lecture.LectureModifyForm;
import com.course_registration.courseRegistration.Service.LectureService;
import com.course_registration.courseRegistration.Service.MemberService;
import com.course_registration.courseRegistration.domain.Lecture;
import com.course_registration.courseRegistration.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final LectureService lectureService;

    @GetMapping("/admin/managerPage")  //관리자 페이지를 보여줌
    public String showManagerPage(Model model, Principal principal){

        String loginId=principal.getName();

        Member member=memberService.findByLoginId(loginId);

        List<Lecture> lectureList=lectureService.getAllLectures();

        model.addAttribute("nickName",member.getNickName());  //관리자페이지로  현재 로그인한 사람의 이름 전달
        model.addAttribute("lectureList",lectureList);  //관리자페이지로 전체 강의 리스트 전달

        return "managerPage";
    }

    @GetMapping("/admin/modifyLecture/{lectureId}")  //강의 정보를 수정하는 폼을 보여줌
    public String showModifyLecture(Model model, @PathVariable(name="lectureId")Long lectureId){

        Lecture lecture=lectureService.getLecture(lectureId);

        LectureModifyForm lectureModifyForm=new LectureModifyForm(lecture);

        model.addAttribute("lectureModifyForm",lectureModifyForm);
        model.addAttribute("lectureId",lectureId);

        return "modifyLecture";
    }


    @PostMapping("/admin/modifyLecture/{lectureId}") //강의 정보 수정
    public String showModifyLecture( Model model, @Validated LectureModifyForm lectureModifyForm,BindingResult bindingResult,  @PathVariable(name="lectureId")Long lectureId){

        Lecture lecture=lectureService.getLecture(lectureId);

        if(bindingResult.hasErrors()){   //bindingResult 객체를 사용하여 @Validated가 붙은 강의수정 폼에 대한 검증에 오류가 발생할 경우 수정 폼으로 다시 돌아가게 하여 예외처리함
            return "modifyLecture";
        }

        try{

            lectureService.modifyLecture(lectureModifyForm,lecture);

        }
        catch (Exception e){ //오류 발생 시 다시 강의정보 수정 폼으로 돌아가도록 함
            model.addAttribute("lectureModifyForm",new LectureModifyForm(lecture));
            model.addAttribute("lectureId",lectureId);

            return "modifyLecture";
        }

        return "redirect:/admin/managerPage";
    }

}
