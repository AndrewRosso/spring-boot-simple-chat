package com.example.simplechat.controller;

import com.example.simplechat.entity.Member;
import com.example.simplechat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {
    public static final String LOGIN_FORM = "loginForm";
    public static final String REGISTER_FORM = "registerForm";
    public static final String CHAT_PAGE = "chatPage";
    public static final String REDIRECT_LOGIN = "redirect:/login";
    public static final String REDIRECT_REGISTER = "redirect:/register";
    public static final String LOGIN = "login";
    private final MemberService memberService;

    @GetMapping("/chat")
    public String chatPage(HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        String loginMember = principal.getName();

        model.addAttribute(LOGIN, loginMember);
        return CHAT_PAGE;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return LOGIN_FORM;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession(true).invalidate();

        return REDIRECT_LOGIN;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("member", new Member());
        return REGISTER_FORM;
    }

    @PostMapping("/register")
    public String registerMember(@Valid Member member) {
        if (memberService.findByLogin(member.getLogin()).orElse(null) != null) {
            return REDIRECT_REGISTER;
        }
        memberService.createMember(member);
        return REDIRECT_LOGIN;
    }
}