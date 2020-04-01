package ru.itis.servlets.services;


public interface MailService{
    void sendMessage(String subject, String mail, String html);
}
