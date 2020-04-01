package ru.itis.servlets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.servlets.dto.SignUpDto;
import ru.itis.servlets.dto.UserDto;
import ru.itis.servlets.models.Role;
import ru.itis.servlets.models.State;
import ru.itis.servlets.models.User;
import ru.itis.servlets.repositories.UsersRepository;
import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {


    private PasswordEncoder passwordEncoder;
    private UsersRepository usersRepository;

    public RegistrationServiceImpl(PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDto loadUserFromParameters(SignUpDto userData) {
        User user = User.builder()
                .mail(userData.getEmail())
                .password(passwordEncoder.encode(userData.getPassword()))
                .nick(userData.getNick())
                .birthDay(userData.getBirthDay())
                .state(State.NOT_CONFIRMED)
                .confirmCode(UUID.randomUUID().toString())
                .login(userData.getLogin())
                .role(Role.USER)
                .build();

        usersRepository.save(user);

        return UserDto.from(user);
    }
}
