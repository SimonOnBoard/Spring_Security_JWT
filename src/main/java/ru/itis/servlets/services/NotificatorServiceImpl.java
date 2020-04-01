package ru.itis.servlets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.servlets.dto.FileDto;
import ru.itis.servlets.dto.UserDto;
import java.util.HashMap;
import java.util.Map;


@Service
public class NotificatorServiceImpl implements NotificatorService {

    private TemplateProcessor templateProcessor;
    private MailService mailService;
    private String serverAddress;

    public NotificatorServiceImpl(String serverConfirmationAddress, TemplateProcessor templateProcessor, MailService mailService) {
        this.serverAddress = serverConfirmationAddress;
        this.templateProcessor = templateProcessor;
        this.mailService = mailService;
    }

    @Override
    public void sendRegistrationNotification(UserDto resultDto) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", resultDto.getName());
        parameters.put("link", serverAddress + resultDto.getCode());
        sendMail(parameters, "mail.ftl", resultDto.getEmail(),"Confirm your registration");
    }

    private void sendMail(Map<String, String> parameters, String template, String email, String subject) {
        String html = templateProcessor.getProcessedTemplate(parameters, template);
        mailService.sendMessage(subject, email, html);
    }

    @Override
    public void sendFileNotification(FileDto resultDto) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("nick",resultDto.getUserName());
        parameters.put("link",resultDto.getUrl());
        parameters.put("fileName",resultDto.getOriginalName());
        sendMail(parameters,"mail_2.ftl",resultDto.getUserEmail(),"File notification");
    }
}
