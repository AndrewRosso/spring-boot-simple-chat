package com.example.simplechat.service;

import com.example.simplechat.entity.Member;
import com.example.simplechat.entity.Role;
import com.example.simplechat.repository.MemberRepository;
import com.example.simplechat.service.impl.MemberServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceImplTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private MemberServiceImpl memberService;

    Member inputMember = Member.builder()
            .login("Input")
            .firstName("Bob")
            .lastName("Marley")
            .password("1234")
            .build();

    Member expectedMember = Member.builder()
            .login("Input")
            .firstName("Bob")
            .lastName("Marley")
            .password("4321")
            .role(Collections.singletonList(new Role("MEMBER")))
            .build();

    @Test
    public void shouldCreateMemberIfMemberDoesNotExist() {
        Mockito.when(memberRepository.findById(inputMember.getLogin())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(inputMember.getPassword())).thenReturn("4321");
        Mockito.when(memberRepository.save(expectedMember)).thenReturn(expectedMember);

        Member createdMember = memberService.createMember(inputMember);

        assertEquals(expectedMember, createdMember);
    }

    @Test
    public void shouldNotCreateMemberIfMemberExist() {
        Mockito.when(memberRepository.findById(inputMember.getLogin())).thenReturn(Optional.of(inputMember));

        Member createdMember = memberService.createMember(inputMember);

        assertNull(createdMember);
    }
}
