package com.course_registration.courseRegistration.domain;



import com.course_registration.courseRegistration.Config.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Member implements UserDetails {  //멤버엔터티

    @Id
    @Column(name="memberId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;   //식별번호(PK)

    private String loginId; //학번

    private String loginPw; //비밀번호

    private String nickName;  //이름

    private String email; //이메일

    private LocalDateTime regDate=LocalDateTime.now();  //학생 생성 시각

    private Long grade;  //학년

    private String department;//학과

    private Long currentCredits; //현재 수강 학점



    @Enumerated(EnumType.STRING)
    private Role authority;   // 회원의 등급(학생 or 관리자)

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<LectureApply> lectureApplyList=new ArrayList<>();


    public static Member createMember(String loginId,String loginPw,String nickName,Role authority,String email,Long grade,String department,Long currentCredits){   //학생 생성(member 인스턴스 생성)
        Member member=new Member();
        member.loginId=loginId;
        member.loginPw=loginPw;
        member.nickName=nickName;
        member.authority=authority;
        member.email=email;
        member.grade=grade;
        member.department=department;
        member.currentCredits=currentCredits;

        return member;
    }

    public static Member createAdmin(String loginId,String email, String loginPw,String nickName,Role authority){ //관리자 생성(관리자 인스턴스)
        Member member=new Member();
        member.loginId=loginId;
        member.email=email;
        member.loginPw=loginPw;
        member.nickName=nickName;
        member.authority=authority;

        return member;
    }

    private boolean isAccountNonExpired= true;
    private boolean isAccountNonLocked=true;
    private boolean isCredentialsNonExpired=true;
    private boolean isEnabled=true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.authority.getValue()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return loginPw;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setCurrentCredits(Lecture lecture){
        this.currentCredits+=lecture.getCredit();
    }





}
