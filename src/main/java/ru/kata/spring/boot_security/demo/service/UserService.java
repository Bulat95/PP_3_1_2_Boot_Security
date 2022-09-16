package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    void saveUser(User user);

    void updateUser(User user, Long id);

    User getUserById(Long id);

    void deleteUserById(Long id);

    Set<Role> getRoles(User UserDetails);

    User getUserDatails();


}