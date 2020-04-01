package ru.itis.servlets.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.servlets.dto.FileDto;
import ru.itis.servlets.services.NotificatorService;

@Aspect
@Component
public class MailSenderAspect {
    @Autowired
    private NotificatorService notificatorService;

    @AfterReturning(pointcut = "execution(* ru.itis.servlets.services.FileService.saveFile(..))",
            returning = "resultDto")
    public void logAfterReturning(JoinPoint joinPoint, FileDto resultDto) {
        System.out.println("hijacked : " + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("******");
        notificatorService.sendFileNotification(resultDto);
    }
}
