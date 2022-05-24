package com.example.simplechat.service.impl;

import com.example.simplechat.entity.Member;
import com.example.simplechat.entity.Role;
import com.example.simplechat.repository.MemberRepository;
import com.example.simplechat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member createMember(Member member) {
        Optional<Member> memberFromDB = memberRepository.findById(member.getLogin());

        if (!memberFromDB.isPresent()) {
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            member.setRole(Collections.singletonList(new Role("MEMBER")));
            memberRepository.save(member);
           return member;
        } else {
            return null;
        }
    }
}
