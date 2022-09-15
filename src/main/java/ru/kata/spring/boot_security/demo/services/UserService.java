package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    void saveUser(User user);
    void updateUser(User user, Long id);
    User getUserById(Long id);
    void deleteUserById(Long id);

    User getUserByName(String username);

}