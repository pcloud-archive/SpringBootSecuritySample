package com.pcloud.sessionsecurity.controller;

import com.pcloud.sessionsecurity.domain.Member;
import com.pcloud.sessionsecurity.dto.MemberDto;
import com.pcloud.sessionsecurity.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("join")
    public String join(@RequestBody MemberDto memberDto) {
        memberService.join(memberDto.getEmail(), memberDto.getName(), memberDto.getPassword());
        return "Join";
    }

    @GetMapping("/login")
    public String loginTest() {
        log.info("LoginTest");
        return "Login";
    }

    @GetMapping("/list")
    public List<MemberDto> getMembers() {
        return memberService.findAll()
                .stream()
                .map(m -> new MemberDto(m.getEmail(), m.getName(), m.getPassword()))
                .collect(Collectors.toList());
    }

    @GetMapping("byname/{name}")
    public List<MemberDto> getMembersByName(@PathVariable(name="name") String name) {
        return memberService.findsByName(name)
                .stream()
                .map(m-> new MemberDto(m.getEmail(), m.getName(), m.getPassword()))
                .collect(Collectors.toList());
    }

    @GetMapping("byemail/{email}")
    public MemberDto getMemberByEmail(@PathVariable(name = "email") String email) {
        Member byEmail = memberService.findByEmail(email);
        return new MemberDto(byEmail.getEmail(), byEmail.getName(), byEmail.getPassword());
    }

}
