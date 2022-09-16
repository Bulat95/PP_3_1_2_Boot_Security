package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {


    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user, Long id) {
        Optional<User> optional = repository.findById(id);
        repository.save(optional.get());
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        Optional<User> optional = repository.findById(id);
        return optional.get();
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        repository.deleteById(id);
    }

    public Set<Role> getRoles(User userDetails){
        return userDetails.getRoles();
    }
    public User getUserDatails(){
        return (User) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found ", username));
        }
        return user;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
