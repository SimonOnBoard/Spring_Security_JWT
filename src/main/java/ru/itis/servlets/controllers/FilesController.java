package ru.itis.servlets.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.servlets.dto.FileDto;
import ru.itis.servlets.security.defails.UserDetailsImpl;
import ru.itis.servlets.services.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FilesController {
    private FileService fileService;

    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/files", method = RequestMethod.POST)
    @ResponseBody
    public FileDto uploadFile(@RequestParam("file") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl user) {
        FileDto message = fileService.saveFile(multipartFile, user);
        return message;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/files/{file-name:.+}", method = RequestMethod.GET)
    public ModelAndView getFile(@PathVariable("file-name") String fileName, @AuthenticationPrincipal UserDetailsImpl user, HttpServletResponse httpServletResponse) throws IOException {
        String[] arguments = fileService.getFile(fileName, user);
        ModelAndView modelAndView = new ModelAndView();
        if (arguments[0].equals("Error")) {
            modelAndView.setViewName("file_upload");
            modelAndView.addObject("message", arguments[1]);
        } else {
            fileService.writeToResponse(arguments, httpServletResponse);
        }
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public ModelAndView uploadFile(@AuthenticationPrincipal UserDetailsImpl user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("file_upload");
        modelAndView.addObject("files",fileService.getFiles(user.getUser().getId()));
        return modelAndView;
    }

}
