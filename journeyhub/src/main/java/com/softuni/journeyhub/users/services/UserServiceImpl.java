package com.softuni.journeyhub.users.services;

import com.softuni.journeyhub.users.entities.Role;
import com.softuni.journeyhub.users.entities.User;
import com.softuni.journeyhub.users.exception.UserNotFoundException;
import com.softuni.journeyhub.users.repositories.RoleRepository;
import com.softuni.journeyhub.users.repositories.UserRepository;
import com.softuni.journeyhub.users.repositories.binding.RegisterBindingModel;
import com.softuni.journeyhub.users.repositories.binding.UserEditBindingModel;
import com.softuni.journeyhub.users.repositories.binding.UserViewBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final BCryptPasswordEncoder encoder;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder encoder, ModelMapper modelMapper, UserRepository userRepository, RoleRepository roleRepository) {
        this.encoder = encoder;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(RegisterBindingModel bindingModel) {
        User user = this.modelMapper.map(bindingModel, User.class);
        user.setPassword(this.encoder.encode(bindingModel.getPassword()));
        Role authority = this.roleRepository.findByAuthority("ROLE_USER");
        HashSet<Role> roles = new HashSet<>();
        roles.add(authority);
        user.setAuthorities(roles);
        this.userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getByUsername(username);
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.getById(id);
    }

    @Override
    public List<UserViewBindingModel> getAllUsers() {
        List<UserViewBindingModel> userViewBindingModels = new ArrayList<>();
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            userViewBindingModels.add(this.modelMapper.map(user, UserViewBindingModel.class));
        }
        return userViewBindingModels;
    }

    @Override
    public UserEditBindingModel getUserEditById(Long id) {
        User user = this.userRepository.getById(id);
        if(user == null){
            throw new UserNotFoundException();
        }
        UserEditBindingModel userEditBindingModel = this.modelMapper.map(user,UserEditBindingModel.class);
        boolean isModerator = false;
        for (GrantedAuthority authority : user.getAuthorities()) {
            if(authority.getAuthority().equals("ROLE_MODERATOR")){
                isModerator = true;
            }
        }
        userEditBindingModel.setModerator(isModerator);
        return userEditBindingModel;
    }

    @Override
    public void updateUser(UserEditBindingModel bindingModel, Long id, Boolean moderator) {
        User user = this.userRepository.getById(id);
        if(user == null){
            throw new UserNotFoundException();
        }
        user.setUsername(bindingModel.getUsername());
        user.setEmail(bindingModel.getEmail());
        if(moderator){
            Role authority = this.roleRepository.findByAuthority("ROLE_USER");
            HashSet<Role> roles = new HashSet<>();
            roles.add(authority);
            authority = this.roleRepository.findByAuthority("ROLE_MODERATOR");
            roles.add(authority);
            user.setAuthorities(roles);
        }else{
            Role authority = this.roleRepository.findByAuthority("ROLE_USER");
            HashSet<Role> roles = new HashSet<>();
            roles.add(authority);
            user.setAuthorities(roles);
        }
        this.userRepository.save(user);
    }

    @Override
    public void update(User user) {
        this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = this.userRepository.getByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User does not exist in database.");
        }
        return user;
    }
}
