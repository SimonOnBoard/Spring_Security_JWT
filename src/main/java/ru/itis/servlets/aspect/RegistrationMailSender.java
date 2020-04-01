package ru.itis.servlets.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.servlets.dto.UserDto;
import ru.itis.servlets.services.NotificatorService;

@Aspect
@Component
public class RegistrationMailSender {
    @Autowired
    private NotificatorService notificatorService;

    @AfterReturning(pointcut = "execution(* ru.itis.servlets.services.RegistrationService.loadUserFromParameters(..))", returning = "resultDto")
    public void logAfterReturning(JoinPoint joinPoint, UserDto resultDto) {
        System.out.println("Signature: " + joinPoint.getSignature().getDeclaringTypeName());
        System.out.println("******");
        notificatorService.sendRegistrationNotification(resultDto);
    }
}
