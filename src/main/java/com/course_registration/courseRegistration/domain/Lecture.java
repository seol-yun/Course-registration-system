package com.course_registration.courseRegistration.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Lecture {

    @Id
    @Column(name="lectureId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lectureId;  //식별자

    private String department; // 학과

    private Long forGrade; //대상 학년

    private Long credit; //학점

    private String subject; //과목명

    private String subjectNumber; //과목번호

    private String classNum; //분반

    private String professorName; //교수명

    private Long maxNum; //정원

    private Long currentNum; //현재 수강신청 인원

    private LocalDateTime regDate=LocalDateTime.now(); //강의 생성 시각


    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<LectureApply> lectureApplyList=new ArrayList<>();   //해당 강의에 대한 수강신청 리스트

    public static Lecture makeLecture(String department, Long forGrade,Long credit,String subject,String subjectNumber,String classNum,String professorName,Long maxNum,Long currentNum){ //강의 인스턴스 생성
        Lecture lecture=new Lecture();

        lecture.department=department;
        lecture.forGrade=forGrade;
        lecture.credit=credit;
        lecture.subject=subject;
        lecture.subjectNumber=subjectNumber;
        lecture.classNum=classNum;
        lecture.professorName=professorName;
        lecture.maxNum=maxNum;
        lecture.currentNum=currentNum;

        return lecture;
    }

    public void setCurrentNum(){  //현재 수강인원 설정(수강신청 할 경우)
        this.currentNum+=1;

    }


    public void cancelLectureCurrentNum(){  //수강을 취소할 경우
        this.currentNum-=1;
    }  //수강취소한 학생이 있을 시 해당 강의의 현재 수강인원 반영

    public void modifyLecture(String department,Long forGrade,String subjectNumber,String classNum,String subject,String professorName,Long credit,Long maxNum){ //강의 객체의 정보를 수정
        this.department=department;
        this.forGrade=forGrade;
        this.subjectNumber=subjectNumber;
        this.classNum=classNum;
        this.subject=subject;
        this.professorName=professorName;
        this.credit= credit;
        this.maxNum=maxNum;

    }






}
