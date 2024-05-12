package com.community.Community.Controller.ServerSide;

import com.community.Community.Services.UserServices.UserService;
import com.community.Community.models.Users.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.community.Community.dto.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;


@Controller
public class AuthController {

    private UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home(){
        return "Login/index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "Login/register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){

        User existingUser = userService.findUserByEmail(userDto.getEmail());
        User existingUser1 = userService.findUserByUsername(userDto.getUsername());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(existingUser1 != null && existingUser1.getUsername() != null && !existingUser1.getUsername().isEmpty()){
            result.rejectValue("username", null,
                    "There is already an account registered with the same username");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "Login/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "Entry/Users";
    }

    @GetMapping("/login")
    public String login(){
        return "Login/login";
    }


}
