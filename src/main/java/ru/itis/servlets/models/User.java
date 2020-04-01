package ru.itis.servlets.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements Serializable {
    private Long id;
    private Date birthDay;
    private String nick;
    private String login;
    private String password;
    private String mail;
    private State state;
    private String confirmCode;
    private Role role;
}
