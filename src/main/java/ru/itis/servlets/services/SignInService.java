package ru.itis.servlets.services;


import ru.itis.servlets.dto.SignInDto;
import ru.itis.servlets.dto.TokenDto;

public interface SignInService {
    TokenDto signIn(SignInDto signInData);
}
