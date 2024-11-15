package com.example.spring.springbootsecurityinky0.service;

import com.example.spring.springbootsecurityinky0.entity.Member;
import com.example.spring.springbootsecurityinky0.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public void join(Member member) {
        System.out.println("저장 하나요");
        System.out.println(member.getEmail()+"  "+member.getNickname()+"  "+member.getUserRole());
        memberRepository.save(member);
        System.out.println("저장했어요");
    }
}
