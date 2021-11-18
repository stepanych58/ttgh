package com.stbegradleapp.fixer.controllers;

import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.UserRole;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import com.stbegradleapp.fixer.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Controller
@RequestMapping("/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
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
