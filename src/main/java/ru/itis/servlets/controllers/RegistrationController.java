package ru.itis.servlets.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.servlets.dto.SignUpDto;
import ru.itis.servlets.dto.UserDto;
import ru.itis.servlets.security.defails.UserDetailsImpl;
import ru.itis.servlets.services.RegistrationService;

@Controller
public class RegistrationController {
    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView getRegistrationPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails != null) return new ModelAndView("redirect:/files");
        return new ModelAndView("registration");
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView completeRegistrationProcess(SignUpDto signUpDto) {
        UserDto userDto = registrationService.loadUserFromParameters(signUpDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pls_confirm");
        modelAndView.addObject("user",userDto.toString());
        return modelAndView;
    }
}
