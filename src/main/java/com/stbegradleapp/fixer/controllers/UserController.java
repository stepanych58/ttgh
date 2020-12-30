package com.stbegradleapp.fixer.controllers;

import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.UserRole;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private FixerUserRepository userRepository;

    @PostMapping("/create_user")
    public String createUser(Model model, @RequestParam Map<String,String> allParams) {
        System.out.println("allParams: " + allParams);
        FixerUser user= new FixerUser(allParams.get("name"),allParams.get("number"), UserRole.CLIENT);
        userRepository.save(user);
        model.addAttribute("userName", user.getName());
        return "createdUser.html";
    }
}
