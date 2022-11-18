package com.course_registration.courseRegistration.Dao;

import com.course_registration.courseRegistration.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> { //member 엔터티에 의해 만들어진 db에 접근하는 메소드를 사용하기 위한 인터페이스
    Optional<Member> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);


}
