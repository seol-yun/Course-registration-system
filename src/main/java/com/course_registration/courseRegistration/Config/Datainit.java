package com.course_registration.courseRegistration.Config;


import com.course_registration.courseRegistration.Dao.MemberRepository;
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

        public void initBoardAndAdmin(){   //초기 데이터 생성
            BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();



            Member admin= Member.createAdmin(
                    "admin",
                    "wnsghks4104@gmail.com",
                    bCryptPasswordEncoder.encode("admin"),
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
                    Long.valueOf(0)

            );
            memberRepository.save(s1);



        }



    }
}
