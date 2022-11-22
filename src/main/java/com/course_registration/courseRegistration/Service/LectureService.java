package com.course_registration.courseRegistration.Service;


import com.course_registration.courseRegistration.Config.Role;
import com.course_registration.courseRegistration.DTO.Lecture.LectureModifyForm;
import com.course_registration.courseRegistration.Dao.LectureRepository;
import com.course_registration.courseRegistration.domain.Lecture;
import com.course_registration.courseRegistration.domain.LectureApply;
import com.course_registration.courseRegistration.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LectureService {

    private final LectureRepository lectureRepository;
    private final JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS="wnsghks4104@gmail.com";

    public List<Lecture> getAllLectures(){ //모든 강의가 포함된 강의 리스트를 출력
        List<Lecture> lectureList=lectureRepository.findAll();

        return lectureList;
    }

    public Lecture getLecture(Long lectureId){  //강의의 pk인 lectureId로 강의를 찾도록 함
        Optional<Lecture> findLecture=lectureRepository.findById(lectureId);

        findLecture.orElseThrow(
                ()->new NoSuchElementException("해당 강의는 존재하지 않습니다.")  //lectureId로 강의를 찾으려할 때 해당되는 강의가 없을 때 예외처리
        );

        Lecture lecture=findLecture.get();

        return lecture;

    }

    public List<Lecture> findListBySearchKeyword(String department,String forGrade,String professorName,String subject,String subjectNumber){ //검색 조건에 따른 강의 찾기
        List<Lecture> lectureList=lectureRepository.findAll();

        List<Integer> conditionIndexList=new ArrayList<>(); //검색어 키워드를 입력한 조건들의 인덱스를 저장하는 배열

        Map<Integer, String> map1 = new HashMap<>();  //매개변수(검색 조건)들을 담을 맵

        map1.put(0,department);     //매개변수로 들어온 조건 순서대로 조건들의 인덱스를 매겨 map을 이용해 매핑함
        map1.put(1,forGrade);
        map1.put(2,professorName);
        map1.put(3,subject);
        map1.put(4,subjectNumber);

        for(int key:map1.keySet()){
            String value=map1.get(key);

            if(!value.equals("")){    //각 검색어 키워드가 공백이 아닌 무언가 입력했을 때 해당 인덱스를 배열에 담음
                conditionIndexList.add(key);
            }
        }

        List<Lecture> findList=new ArrayList<>(); //최종적으로 리턴, 즉 입력한 검색조건에 따른 강의들


        if(conditionIndexList.size()!=0){
            for(int i=0;i<lectureList.size();i++) {
                Map<Integer, String> map2 = new HashMap<>();

                List<Lecture> tempList = new ArrayList<>();

                map2.put(0, lectureList.get(i).getDepartment());
                map2.put(1, Long.toString(lectureList.get(i).getForGrade()));
                map2.put(2, lectureList.get(i).getProfessorName());
                map2.put(3, lectureList.get(i).getSubject());
                map2.put(4, lectureList.get(i).getSubjectNumber());     //모든 강의들 중 각 강의에 대해 map을 생성하여 위에서 설정한 조건들에 대한 인덱스에 맞게 매핑해줌


                for (int k = 0; k < conditionIndexList.size(); k++) {

                    if (map1.get(conditionIndexList.get(k)).equals(map2.get(conditionIndexList.get(k)))) {   //학생이 입력한 특정 조건의 키워드가 강의의 정보와 일치할 때 해당 강의를 임시 배열에 저장
                        tempList.add(lectureList.get(i));
                    }
                }

                if (tempList.size() == conditionIndexList.size()) {   //학생이 입력한 조건 키워드 개수와 임시 배열에 저장한 강의의 개수가 같을 때 리턴할 최종 배열에 해당 강의를 넣음
                    findList.add(tempList.get(0));                    //ex)학과와 학년 키워드를 입력했을 때(총 2개) 전체 강의 중 i번째에 해당하는 강의에 정보와 입력한 조건들이 일치할 때마다
                                                                      // 해당 강의를 임시 배열에 넣음(해당 강의의 정보가 입력한 학과와 학년과 일치했을 경우 총 2번을 넣게 됨)
                }

            }
        }
        else{   //조건을 아무것도 입력하지 않았을 경우 전체리스트를 출력
            findList=lectureList;
        }
        return findList;

    }

    public Lecture findBySubjectNumAndClassNum(String subjectNumber, String classNum){ //과목번호와 분반을 통해 강의를 찾을 수 있도록 함
        List<Lecture> lectureList=getAllLectures();

        Lecture lecture=null;

        for(int i=0;i<lectureList.size();i++){
            if((lectureList.get(i).getSubjectNumber().equals(subjectNumber))&&(lectureList.get(i).getClassNum().equals(classNum))){

                lecture=lectureList.get(i);
                break;
            }
        }


        return lecture;
    }

    public List<Lecture> findLectureListByMember(Member member){ //학생 객체를 통해 학생이 수강신청한 강의의 리스트를 리턴

        List<Lecture> lectureList=new ArrayList<>();

        List<LectureApply> lectureApplyList=member.getLectureApplyList();



        for(int i=0;i<lectureApplyList.size();i++){
            Lecture findLecture=findBySubjectNumAndClassNum(lectureApplyList.get(i).getSubjectNumber(),lectureApplyList.get(i).getClassNum());
            lectureList.add(findLecture);

        }

        return lectureList;

    }

    @Transactional
    public void modifyLecture(LectureModifyForm lectureModifyForm,Lecture lecture){  //강의 정보 수정
        lecture.modifyLecture(
                lectureModifyForm.getDepartment(),
                lectureModifyForm.getForGrade(),
                lectureModifyForm.getSubjectNumber(),
                lectureModifyForm.getClassNum(),
                lectureModifyForm.getSubject(),
                lectureModifyForm.getProfessorName(),
                lectureModifyForm.getCredit(),
                lectureModifyForm.getMaxNum()
        );

        List<LectureApply> lectureApplyList=lecture.getLectureApplyList();
        for(int i=0; i<lectureApplyList.size();i++){  //강의의 학점을 변경한 학점으로 업데이트
            lectureApplyList.get(i).getMember().updateCurrentCredits(lectureModifyForm.getCredit());
        }


    }

    @Transactional
    public void deleteLecture(Lecture lecture){ //강의 삭제

        try{

            List<LectureApply> lectureApplyList=lecture.getLectureApplyList();

            MimeMessage mail=javaMailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail,true,"UTF-8");
            mailHelper.setFrom(FROM_ADDRESS);  //누가 보낼것인지

            for(int i=0;i<lectureApplyList.size();i++){  //해당 강의를 수강 신청한 사람 수 만큼 각각 메일을 전송하도록 함

                lectureApplyList.get(i).getMember().cancelLectureCurrentCredits(lecture);

                mailHelper.setTo(lectureApplyList.get(i).getMember().getEmail());  //누가 받을 것인지
                mailHelper.setSubject("수강신청 시스템에서 강의 폐강을 안내드립니다.");
                mailHelper.setText(lectureApplyList.get(i).getMember().getNickName()+"님이 신청하신 "+lecture.getSubject()+" 강의가 폐강되었음을 안내드립니다.");
                javaMailSender.send(mail); //메일 전송

            }

            lectureRepository.delete(lecture);
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    @Transactional
    public void setAllowedLectureApplyTime(LocalDateTime startTime, LocalDateTime endTime, List<Member> memberList){ //수강신청 시작시간 및 종료시간 설정

        for(int i=0;i<memberList.size();i++){
            if(memberList.get(i).getAuthority()== Role.ADMIN){  //시작시간과 종료시간 세팅
                memberList.get(i).setCanApplyStartTime(startTime);
                memberList.get(i).setCanApplyEndTime(endTime);
            }
        }

    }

}
