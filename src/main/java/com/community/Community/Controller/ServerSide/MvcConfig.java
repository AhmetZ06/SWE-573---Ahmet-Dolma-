package com.community.Community.Controller.ServerSide;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("Login/home");
        registry.addViewController("/").setViewName("Login/home");
        registry.addViewController("/hello").setViewName("Login/hello");
        registry.addViewController("/login").setViewName("Login/login");
        registry.addViewController("/register").setViewName("Login/register");
        registry.addViewController("/createCommunity").setViewName("Communities/createCommunity");
    }

}