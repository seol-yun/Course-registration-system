package com.course_registration.courseRegistration.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class LectureApply {

    @Id
    @Column(name="lectureApplyId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureApplyId;

    private String subjectNumber; //과목번호

    private String classNum; //분반

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;   //회원은 여러개의 수강 신청이 가능함

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="lectureId")
    private Lecture lecture; // 여러개의 수강신청은 각각 하나의 강의에만 신청이 가능함

    public static LectureApply createApply(String subjectNumber, String classNum){ //수강 신청 인스턴스 1개 생성
        LectureApply lectureApply=new LectureApply();

        lectureApply.subjectNumber=subjectNumber;
        lectureApply.classNum=classNum;

        return lectureApply;
    }

    public void setMember(Member member){   //수강 신청을 한 학생에 대한 setting
        this.member=member;
        member.getLectureApplyList().add(this);
    }

    public void setLecture(Lecture lecture){ //수강 신청을 한 강의에 대한 setting
        this.lecture=lecture;
        lecture.getLectureApplyList().add(this);
    }


}
