package com.avitam.bankloanapplication.web.controllers.admin.user;

import com.avitam.bankloanapplication.core.service.UserService;
import com.avitam.bankloanapplication.model.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_CLIENT')")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

//    @PostMapping("/signin")
//    public String signIn(@Valid @RequestBody UserLoginDTO userLoginDTO) {
//        return userService.signIn(userLoginDTO.getUsername(), userLoginDTO.getPassword());
//    }

//    @PostMapping("/signup")
//    public String signUp(@RequestBody @Valid UserDataDTO userDataDTO) {
//        User user = new User();
//        user.setUsername(userDataDTO.getUsername());
//        user.setEmail(userDataDTO.getEmail());
//        user.setPassword(userDataDTO.getPassword());
//        return userService.signUp(user, false);
//    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{username}")
    public String delete(@PathVariable String username) {
        userService.delete(username);
        return username + " was deleted successfully";
    }

//    @GetMapping(value = "/search/{username}")
//    public User searchByUserName(@PathVariable String username) {
//        return userService.search(username);
//    }

}