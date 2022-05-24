package com.example.simplechat.controller;

import com.example.simplechat.entity.Member;
import com.example.simplechat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String showLoginForm(
            @RequestParam(value = "error", defaultValue = "false") boolean loginError, Model model) {
        if (loginError) {
            String errorMessage = "Login and password do not match";
            model.addAttribute("errorMessage", errorMessage);
        }
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
    public String registerMember(@Valid Member member,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return REGISTER_FORM;
        } else if (memberService.createMember(member)==null) {
            model.addAttribute("message", "A user with this login already exists");
            return REGISTER_FORM;
        } else {
            memberService.createMember(member);
            return REDIRECT_LOGIN;
        }
    }
}