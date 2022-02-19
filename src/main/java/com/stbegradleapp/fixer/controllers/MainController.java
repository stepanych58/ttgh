package com.stbegradleapp.fixer.controllers;

import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.params.user.UserRole;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import com.stbegradleapp.fixer.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    FixerUserRepository fixerUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = { "/", "/{x:[\\w\\-]+}", "/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}" })
    public String getIndex(HttpServletRequest request) {
        return "/website";
    }

    @GetMapping(path = "/admin")
    public String admin()
    {
        return "admin";
    }

    @GetMapping(path = "/reg")
    public String reg()
    {
        return "registrationForm";
    }

    @GetMapping(path = "/out")
    public String out()
    {
        return "success";
    }

    @PostMapping(value = "registr")
    public String newUser(@RequestParam Map<String, String> req) {
        String pswd = req.get("pswd");
        System.out.println(pswd);
        FixerUser user = new FixerUser(
                req.get("name"),
                passwordEncoder.encode(pswd),
                req.get("phone"),
                UserRole.CLIENT
        );
        fixerUserRepository.save(user);
        return "redirect:/";
    }

}
