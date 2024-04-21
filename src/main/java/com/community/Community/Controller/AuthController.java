package com.community.Community.Controller;

import com.community.Community.models.Users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.community.Community.dto.UserDto;
import com.community.Community.Services.IUserService;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class AuthController {

    private IUserService userService;
    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", consumes = "application/x-www-form-urlencoded")
    public String registrationForm(@Valid @ModelAttribute("user") UserDto userDto,
                                   BindingResult result,
                                   Model model) {
        return processRegistration(userDto, result, model);
    }


    @PostMapping(value = "api/register", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> registrationApi(@Valid @RequestBody UserDto userDto,
                                             BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        userService.saveUser(userDto);
        return ResponseEntity.ok("User registered successfully");
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
}
