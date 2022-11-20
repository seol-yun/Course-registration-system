package com.course_registration.courseRegistration.Config;


import com.course_registration.courseRegistration.Dao.LectureRepository;
import com.course_registration.courseRegistration.Dao.MemberRepository;
import com.course_registration.courseRegistration.domain.Lecture;
import com.course_registration.courseRegistration.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class Datainit {

    private final InitService initService;
    private final MemberRepository memberRepository;


    @PostConstruct
    public void init(){


        if(!memberRepository.existsByLoginId("admin")){
            initService.initBoardAndAdmin();
        }

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class InitService{

        private final MemberRepository memberRepository;
        private final LectureRepository lectureRepository;

        public void initBoardAndAdmin(){   //초기 데이터 생성
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();



            Member admin= Member.createAdmin(
                    "admin",
                    "wnsghks4104@gmail.com",
                    bCryptPasswordEncoder.encode("admin"),
                    "관리자",
                    Role.ADMIN


                    );

            memberRepository.save(admin);

            Member s1=Member.createMember(
                "201902695",
                    bCryptPasswordEncoder.encode("aaa123"),
                    "박준환",
                    Role.MEMBER,
                    "wnsghks4104@naver.com",
                    Long.valueOf(4),
                    "컴퓨터융합학부",
                    Long.valueOf(0)

            );
            memberRepository.save(s1);

            Member s2=Member.createMember(
                    "201902703",
                    bCryptPasswordEncoder.encode("123"),
                    "설윤환",
                    Role.MEMBER,
                    "answk1025@naver.com",
                    Long.valueOf(2),
                    "컴퓨터융합학부",
                    Long.valueOf(0)

            );
            memberRepository.save(s2);

            Member s3=Member.createMember(
                    "201902740",
                    bCryptPasswordEncoder.encode("bbb123"),
                    "이호준",
                    Role.MEMBER,
                    "201902695@o.cnu.ac.kr",
                    Long.valueOf(3),
                    "컴퓨터융합학부",
                    Long.valueOf(0)

            );
            memberRepository.save(s3);

            Member s4=Member.createMember(
                    "201902700",
                    bCryptPasswordEncoder.encode("123"),
                    "설치",
                    Role.MEMBER,
                    "201902703@o.cnu.ac.kr",
                    Long.valueOf(2),
                    "컴퓨터융합학부",
                    Long.valueOf(0)

            );
            memberRepository.save(s4);

            Member s5=Member.createMember(
                    "201902696",
                    bCryptPasswordEncoder.encode("aaa123"),
                    "박상인",
                    Role.MEMBER,
                    "201902696@o.cnu.ac.kr",
                    Long.valueOf(4),
                    "기계공학과",
                    Long.valueOf(0)

            );
            memberRepository.save(s5);

            Member s6=Member.createMember(
                    "201702740",
                    bCryptPasswordEncoder.encode("ccc123"),
                    "홍길동",
                    Role.MEMBER,
                    "201702740@o.cnu.ac.kr",
                    Long.valueOf(4),
                    "컴퓨터융합학부",
                    Long.valueOf(0)

            );
            memberRepository.save(s6);

            Lecture l1= Lecture.makeLecture(
                    "컴퓨터융합학부",
                    Long.valueOf(3),
                    Long.valueOf(3),
                    "데이터베이스",
                    "1217-3333",
                    "00",
                    "이규철",
                    Long.valueOf(5),
                    Long.valueOf(0)

            );
            lectureRepository.save(l1);

            Lecture l2= Lecture.makeLecture(
                    "컴퓨터융합학부",
                    Long.valueOf(1),
                    Long.valueOf(3),
                    "선형대수",
                    "1214-1003",
                    "03",
                    "양희철",
                    Long.valueOf(5),
                    Long.valueOf(0)

            );
            lectureRepository.save(l2);

            Lecture l3= Lecture.makeLecture(
                    "컴퓨터융합학부",
                    Long.valueOf(2),
                    Long.valueOf(3),
                    "컴퓨터구조",
                    "1214-2003",
                    "02",
                    "김형식",
                    Long.valueOf(5),
                    Long.valueOf(0)

            );
            lectureRepository.save(l3);

            Lecture l4= Lecture.makeLecture(
                    "컴퓨터융합학부",
                    Long.valueOf(3),
                    Long.valueOf(3),
                    "컴퓨터네트워크",
                    "1214-3006",
                    "02",
                    "이영석",
                    Long.valueOf(5),
                    Long.valueOf(0)

            );
            lectureRepository.save(l4);

            Lecture l5= Lecture.makeLecture(
                    "컴퓨터융합학부",
                    Long.valueOf(4),
                    Long.valueOf(3),
                    "정보보호",
                    "1214-4005",
                    "00",
                    "류재철",
                    Long.valueOf(5),
                    Long.valueOf(0)

            );
            lectureRepository.save(l5);

            Lecture l6= Lecture.makeLecture(
                    "컴퓨터융합학부",
                    Long.valueOf(4),
                    Long.valueOf(3),
                    "정보보호",
                    "1214-4014",
                    "01",
                    "홍길동",
                    Long.valueOf(5),
                    Long.valueOf(0)

            );
            lectureRepository.save(l6);

            Lecture l7= Lecture.makeLecture(
                    "컴퓨터융합학부",
                    Long.valueOf(3),
                    Long.valueOf(3),
                    "데이터베이스 설계",
                    "1214-4999",
                    "00",
                    "이규철",
                    Long.valueOf(5),
                    Long.valueOf(0)

            );
            lectureRepository.save(l7);

        }






    }
}
