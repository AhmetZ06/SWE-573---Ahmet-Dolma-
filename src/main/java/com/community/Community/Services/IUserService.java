package com.community.Community.Services;


import com.community.Community.dto.UserDto;
import com.community.Community.models.Users.User;

import java.util.List;

public interface IUserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);
    User findUserByUsername(String username);

    List<UserDto> findAllUsers();

}