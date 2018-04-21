package com.softuni.journeyhub.users.services;

import com.softuni.journeyhub.users.entities.User;
import com.softuni.journeyhub.users.repositories.binding.RegisterBindingModel;
import com.softuni.journeyhub.users.repositories.binding.UserEditBindingModel;
import com.softuni.journeyhub.users.repositories.binding.UserViewBindingModel;

import java.util.List;

public interface UserService {
    void register(RegisterBindingModel user);
    User getUserByUsername(String username);
    User getUserById(Long id);
    List<UserViewBindingModel> getAllUsers();
    UserEditBindingModel getUserEditById(Long id);
    void updateUser(UserEditBindingModel user, Long id);
    void update(User user);
}
