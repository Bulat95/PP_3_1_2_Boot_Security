package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private UserService service;

    @Autowired
    public MainController(UserService userService) {
        this.service = userService;
    }

    @GetMapping("/user")
    public String printUserPage(ModelMap model, Principal principal) {
        model.addAttribute("authUser", service.loadUserByUsername(principal.getName()));

        User userDetails = (User) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<String> roles = new ArrayList<>();
        for (Role role : userDetails.getRoles()) {
            roles.add(role.getName());
        }
        model.addAttribute("userroles", roles);
        model.addAttribute("userinfo", userDetails);
        return "user";
    }

    @GetMapping(value = {"/"})
    public String getLoginPage() {
        return "/login";
    }

}
