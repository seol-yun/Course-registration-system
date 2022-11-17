package com.course_registration.courseRegistration.Config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role { //회원의 등급 (학생 or 관리자)

    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");



    private String value;
}
