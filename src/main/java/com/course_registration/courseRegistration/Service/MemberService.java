package com.course_registration.courseRegistration.Service;

import com.course_registration.courseRegistration.Config.Role;
import com.course_registration.courseRegistration.Dao.MemberRepository;
import com.course_registration.courseRegistration.domain.LectureApply;
import com.course_registration.courseRegistration.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  //userdetail 인터페이스를 이용해 로그인한 사람의 학번을 불러올수 있도록함

        return memberRepository.findByLoginId(username).get();
    }

    public Member findByLoginId(String loginId) throws IllegalStateException{  //매개변수로 전달받은 학번에 해당하는 학생이 있는지 찾기

        Optional<Member> memberOption=memberRepository.findByLoginId(loginId);
        memberOption.orElseThrow(
                ()-> new IllegalStateException("존재하지 않는 회원입니다.")  //회원이 존재하지 않을 경우 예외처리
        );
        return memberOption.get();

    }

    public List<LectureApply> LectureApplyListByMember(Member member){  //학생 객체를 받아 학생이 수강신청한 강의의 리스트를 출력
        List<LectureApply> lectureApplyList=member.getLectureApplyList();

        return lectureApplyList;

    }

    public List<Member> getMemberList(){  //전체 회원에 대한 리스트를 가져옴(학생 or 관리자)
        List<Member> memberList=memberRepository.findAll();

        return memberList;
    }

    public Member findAdminMember(){ //관리자 객체 리턴
        List<Member> memberList=getMemberList();

        Member findAdmin=null;

        for(int i=0;i<memberList.size();i++){
            if(memberList.get(i).getAuthority()== Role.ADMIN){
               findAdmin=memberList.get(i);
            }
        }

        return findAdmin;
    }


}
