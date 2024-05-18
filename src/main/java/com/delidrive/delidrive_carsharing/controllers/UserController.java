package com.delidrive.delidrive_carsharing.controllers;

import com.delidrive.delidrive_carsharing.models.User;
import com.delidrive.delidrive_carsharing.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage",
                    "Пользователь с email: " + user.getEmail() + " уже существует.");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("rentedAnnouncements", userService.getRentedAnnouncements(principal));
        model.addAttribute("yourAnnouncements", userService.getAnnouncements(principal));
        return "user_profile";
    }

    @PostMapping("/user/{id}/remove")
    public String removeUser(@PathVariable Long id) {
        userService.removeUserById(id);
        return "redirect:/announcement";
    }
}
