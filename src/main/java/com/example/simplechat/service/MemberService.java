package com.example.simplechat.service;

import com.example.simplechat.entity.Member;

import java.util.Optional;

public interface MemberService {
    void createMember(Member member);

    Optional<Member> findByLogin(String login);
}
