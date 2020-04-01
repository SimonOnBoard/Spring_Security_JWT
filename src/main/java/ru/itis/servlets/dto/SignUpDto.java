package ru.itis.servlets.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SignUpDto {
    private String nick;
    private String login;
    private String password;
    private String email;
    private Date birthDay;

}
