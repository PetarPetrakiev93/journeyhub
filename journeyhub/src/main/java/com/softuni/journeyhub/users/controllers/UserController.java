package com.softuni.journeyhub.users.controllers;


import com.softuni.journeyhub.controllers.BaseController;
import com.softuni.journeyhub.users.repositories.binding.RegisterBindingModel;
import com.softuni.journeyhub.users.repositories.binding.UserEditBindingModel;
import com.softuni.journeyhub.users.repositories.binding.UserViewBindingModel;
import com.softuni.journeyhub.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController extends BaseController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private boolean isLoggedIn(HttpServletRequest request){
        return request.getSession().getAttribute("user-id") != null;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false, name = "error") String error,
            @RequestParam(required = false, name = "logout") String logout,
            ModelAndView modelAndView){
        if(error != null) return this.view("login", "error", error);
        if(logout != null) return this.view("login","logout", logout);
        return this.view("login");
    }


    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView){
        return this.view("register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute RegisterBindingModel bindingModel,
                                        ModelAndView modelAndView){
        if(bindingModel.getPassword().equals(bindingModel.getConfirmPassword())){
            this.userService.register(bindingModel);
        }

        return this.redirect("/login");
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/users")
    private ModelAndView showUsers(ModelAndView modelAndView){
        List<UserViewBindingModel> users = this.userService.getAllUsers();
        return this.view("show-users", "users", users);
    }

    @GetMapping("/users/edit/{id}")
    private ModelAndView editUsers(@ModelAttribute UserEditBindingModel userEditBindingModel,
                                   @PathVariable Long id,
            ModelAndView modelAndView){
        UserEditBindingModel user = this.userService.getUserEditById(id);
        return this.view("edit-users", "user", user);
    }

    @PostMapping("/users/edit/{id}")
    private ModelAndView editUserPost(@ModelAttribute UserEditBindingModel userEditBindingModel,
                                   @PathVariable Long id,
                                   ModelAndView modelAndView){
        this.userService.updateUser(userEditBindingModel, id);
        return this.redirect("/users");
    }
}
