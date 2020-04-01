package ru.itis.servlets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.servlets.models.State;
import ru.itis.servlets.models.User;
import ru.itis.servlets.repositories.UsersRepository;

import java.util.Optional;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public String checkConfirmation(String key) {
        Optional<User> user = usersRepository.findByCode(key);
        if (user.isPresent()) {
            User user1 = user.get();
            if(user1.getState().equals(State.CONFIRMED)){
                return "Already confirmed";
            }
            user1.setState(State.CONFIRMED);
            usersRepository.update(user1);
            return "Yes yep check)))";
        }
        return "Activation failed/ Authorization Error";
    }
}
