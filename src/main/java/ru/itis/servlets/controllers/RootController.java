package ru.itis.servlets.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.servlets.security.defails.UserDetailsImpl;

@Controller
public class RootController {
    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getStarter(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails != null) return new ModelAndView("redirect:/profile");
        return new ModelAndView("redirect:/login");
    }
}
