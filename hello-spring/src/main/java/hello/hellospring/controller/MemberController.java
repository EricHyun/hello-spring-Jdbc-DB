package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    //private final MemberService memberService = new MemberService(); 메모리낭비 spring 컨테이너 이용

    private final MemberService memberService; //spring 컨테이너 등록

    /*  Setter이용하기
    @Autowired
    public void setMemberService(MemberService memberService){
        this.memberService = memberService;
    }
*/


    /**
     * MemberController는 Spring 컨테이너가 뜰 때 생성한다
     * 이 때 밑에 생성자 호출 생성자에 Autowired 어노테이션이 붙어있으면
     * memberService를 spring이 spring컨테이너에 있는 memberService를 가져다가 연결해줌
     * DI는 생성자 주입이 제일 좋다 필드나 setter쓰는거 보다.
     */


    @Autowired
    public MemberController(MemberService memberService) { //ctrl + insert
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}



