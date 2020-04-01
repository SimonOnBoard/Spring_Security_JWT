package ru.itis.servlets.services;


import org.springframework.stereotype.Service;
import ru.itis.servlets.dto.SignUpDto;
import ru.itis.servlets.dto.UserDto;


public interface RegistrationService {
    UserDto loadUserFromParameters(SignUpDto params);
}
