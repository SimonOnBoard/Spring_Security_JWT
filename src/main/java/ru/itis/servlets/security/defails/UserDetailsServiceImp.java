//package ru.itis.servlets.security.defails;
//
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import ru.itis.servlets.models.User;
//import ru.itis.servlets.repositories.UsersRepository;
//
//import java.util.Optional;
//
//@Service
//public class UserDetailsServiceImp implements UserDetailsService {
//    private UsersRepository usersRepository;
//
//    public UserDetailsServiceImp(UsersRepository usersRepository) {
//        this.usersRepository = usersRepository;
//    }
//
//    @Override
//    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
//
//    /*Here we are using dummy data, you need to load user data from
//     database or other third party application*/
//        User user = findUserByLogin(username);
//
//        if (user != null) {
//            return UserDetailsImpl.builder().user(user).build();
//        } else {
//            throw new UsernameNotFoundException("User not found.");
//        }
//    }
//
//    private User findUserByLogin(String username) {
//        Optional<User> user = usersRepository.findByLogin(username);
//        return user.orElse(null);
//    }
//}