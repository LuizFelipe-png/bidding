package com.biddy.system.bidding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.biddy.system.bidding.model.UserDTO;
import com.biddy.system.bidding.model.UserRequestDTO;
import com.biddy.system.bidding.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userRequest", new UserRequestDTO());
        return "login";
    }

    @GetMapping("/registrar")
    public String registrar(Model model) {
        model.addAttribute("user", new UserDTO());
        return "registrar";
    }

    @PostMapping("/registrar")
    public String cadastrarUsuario(@ModelAttribute("user") UserDTO userDTO) {
        userService.register(userDTO);
        return "redirect:/login";
    }
}