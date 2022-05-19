package com.example.simplechat.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    public static final String LOGIN_FORM = "loginForm";
    public static final String REDIRECT_LOGIN = "redirect:/login";
    public static final String REDIRECT = "redirect:/";
    public static final String CHAT_PAGE = "chatPage";
    public static final String USERNAME = "username";

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute(USERNAME);
        if (StringUtils.isEmpty(username)) {
            return REDIRECT_LOGIN;
        }

        model.addAttribute(USERNAME, username);
        return CHAT_PAGE;
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return LOGIN_FORM;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, @RequestParam String username) {
        if (StringUtils.isEmpty(username)) {
            return LOGIN_FORM;
        }

        request.getSession().setAttribute(USERNAME, username);
        return REDIRECT;
    }

    @RequestMapping(path = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession(true).invalidate();

        return REDIRECT_LOGIN;
    }

}
