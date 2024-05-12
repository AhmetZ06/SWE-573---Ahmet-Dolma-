package com.community.Community.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import com.community.Community.Repositories.UserRepository;
import com.community.Community.models.Users.User;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession {
    private User currentUser;

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        fetchCurrentUser();
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void fetchCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String currentUsername = authentication.getName();
            User user = userRepository.findByEmail(currentUsername);  // Assuming the username is the email
            setCurrentUser(user);
        }
    }
}
