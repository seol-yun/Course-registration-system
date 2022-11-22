package com.course_registration.courseRegistration.DTO.Lecture;

import com.course_registration.courseRegistration.domain.Lecture;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LectureModifyForm {  //강의 정보 수정 폼

    @NotBlank(message="학과를 입력해주세요.")
    private String department;

    @NotNull(message = "학년을 입력해주세요.")
    private Long forGrade;

    @NotBlank(message="과목번호를 입력해주세요.")
    private String subjectNumber;

    @NotBlank(message="분반을 입력해주세요.")
    private String classNum;

    @NotBlank(message="과목명을 입력해주세요.")
    private String subject;

    @NotBlank(message="교수명을 입력해주세요.")
    private String professorName;

    @NotNull(message = "학점을 입력해주세요.")
    private Long credit;

    @NotNull(message = "정원을 입력해주세요.")
    private Long maxNum;

    public LectureModifyForm(Lecture lecture){  //강의 수정 폼 생성자
        this.department=lecture.getDepartment();
        this.forGrade=lecture.getForGrade();
        this.subjectNumber=lecture.getSubjectNumber();
        this.classNum=lecture.getClassNum();
        this.subject=lecture.getSubject();
        this.professorName= lecture.getProfessorName();
        this.credit= lecture.getCredit();;
        this.maxNum=lecture.getMaxNum();
    }

}
