package ru.itis.servlets.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.servlets.security.defails.UserDetailsImpl;
import ru.itis.servlets.services.ConfirmationService;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class ConfirmationController {
    private ConfirmationService confirmationService;

    public ConfirmationController(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/confirm/{key}", method = RequestMethod.GET)
    public ModelAndView getFile(@PathVariable("key") String key, Authentication authentication) throws IOException {
        if(authentication != null) return new ModelAndView("redirect:/files");
        String answer = confirmationService.checkConfirmation(key);
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("result", answer);
        return new ModelAndView("confirm", hashMap);

    }
}
