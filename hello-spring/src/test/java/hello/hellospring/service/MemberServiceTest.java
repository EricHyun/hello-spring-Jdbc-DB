package hello.hellospring.service;

import hello.hellospring.domain.Member;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;
    /**
     *  clear 만드려고 가져온건데
     *  이렇게 객체를 새로만들면 인스턴스가 다르기 때문에 문제가 생길 수 있다
     *  파일 안에가 static 이면 상관없는데 지금 static이지만 BeforEach이용해서 사용
     */
    @BeforeEach //동작하기 전에 넣어줌
    public void beforEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);

    }


    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }


    @Test
    void 회원가입() {
        //given
        Member member = new Member(); // 데이터
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);//내가 검증하는것


        //then 검증
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    public void 중복회원_예외(){
     //given
     Member member1 = new Member();
     member1.setName("spring");

     Member member2 = new Member();
     member2.setName("spring");

     //when
    memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//다른 예외 넣으면 터짐
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    /* 다른 방법도 있음
    try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */

     //then

    }


    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}