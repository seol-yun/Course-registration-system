package com.course_registration.courseRegistration.DTO.Member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberLoginForm {  //로그인 폼

    @NotBlank(message = "학번을 입력해주세요")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String loginPw;
}
