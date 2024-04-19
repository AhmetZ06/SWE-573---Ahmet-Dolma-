package com.community.Community.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.community.Community.models.*;

@Controller
public class AuthController {

    @GetMapping("/index")
    public String home() {
        return "index";
    }
}
