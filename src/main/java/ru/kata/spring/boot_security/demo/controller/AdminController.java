package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping()
    public String getUsers(ModelMap model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        model.addAttribute("newuser", new User());
        return "users";
    }

    @DeleteMapping(value = "/delete")
    public String deleteUser(@RequestParam(value = "id") long id, Model model) {
        userService.deleteUserById(id);
        return "delete";
    }

    @PutMapping(value = "/add")
    public String saveUser(@ModelAttribute User user,
                           ModelMap model) {
        userService.saveUser(user);
        return getUsers(model);
    }

    @PatchMapping(value = "/redact")
    public String redactionForm(@RequestParam(value = "id") long id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.getUserById(id));
        return "update";
    }

    @GetMapping(value = "/do_redact")
    public String redactionUser(@ModelAttribute User user, ModelMap modelMap) {
        userService.updateUser(user, user.getId());
        return "update";
    }
}