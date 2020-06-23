package ru.itis.servlets.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.servlets.dto.SignInDto;
import ru.itis.servlets.dto.TokenDto;
import ru.itis.servlets.security.defails.UserDetailsImpl;
import ru.itis.servlets.services.SignInService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private SignInService signInService;

    public LoginController(SignInService signInService) {
        this.signInService = signInService;
    }


    @PreAuthorize("permitAll()")
    @GetMapping("/login")
    public ModelAndView getLoginPage(@RequestParam(value = "error", required = false) String error, @AuthenticationPrincipal UserDetailsImpl authentication) {
        if(authentication != null) return new ModelAndView("redirect:/profile");
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("error", error);
        return new ModelAndView("login", parameters);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ModelAndView signIn(HttpServletResponse httpServletResponse, @RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
        TokenDto token = signInService.signIn(SignInDto.builder().email(email).password(password).build());
        Cookie cookie = new Cookie("cookieName", token.getToken());
        httpServletResponse.addCookie(cookie);
        return new ModelAndView("redirect:/profile");
    }

}
