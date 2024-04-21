package com.community.Community.Controller.WithoutAPI;

import com.community.Community.Services.IUserService;
import com.community.Community.dto.UserDto;
import com.community.Community.models.Users.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthControllerAWPI {

    private IUserService userService;

    public AuthControllerAWPI(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register/save", consumes = "application/x-www-form-urlencoded")
    public String registrationForm(@Valid @ModelAttribute("user") UserDto userDto,
                                   BindingResult result,
                                   Model model) {
        return processRegistration(userDto, result, model);
    }

    private String processRegistration(UserDto userDto, BindingResult result, Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());
        User existingUserByUsername = userService.findUserByUsername(userDto.getUsername());

        if (existingUser != null) {
            result.rejectValue("email", null, "There is already an account registered with this email");
        }
        if (existingUserByUsername != null) {
            result.rejectValue("username", null, "There is already an account registered with this username");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }
        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }
}