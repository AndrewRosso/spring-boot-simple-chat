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

    public void createMember(Member member) {
        Member memberFromDB = findByLogin(member.getLogin()).orElse(null);

        if (memberFromDB == null) {
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            member.setRole(Collections.singletonList(new Role("MEMBER")));
            memberRepository.save(member);
        }
    }

    @Override
    public Optional<Member> findByLogin(String login) {
        return memberRepository.findById(login);
    }
}
