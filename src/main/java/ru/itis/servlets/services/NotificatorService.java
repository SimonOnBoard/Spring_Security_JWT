package ru.itis.servlets.services;

import ru.itis.servlets.dto.FileDto;
import ru.itis.servlets.dto.UserDto;

public interface NotificatorService {
    void sendRegistrationNotification(UserDto userDto);
    void sendFileNotification(FileDto fileDto);
}
