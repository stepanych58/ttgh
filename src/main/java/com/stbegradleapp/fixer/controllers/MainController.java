package com.stbegradleapp.fixer.controllers;

import com.stbegradleapp.fixer.FixerConstants;
import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.UserRole;
import com.stbegradleapp.fixer.model.params.OrderParameter;
import com.stbegradleapp.fixer.repositories.OrderRepository;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import com.stbegradleapp.fixer.ui.UIParameterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    FixerUserRepository fixerUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping(path = "/")
    public String home()
    {
        return "website";
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
    
    @PostMapping(path = "/order/")
    public String orderPage(Model model, @RequestParam Map<String,String> allParams)
    {
        String orderId = allParams.get(FixerConstants.ORDER_ID);
        if (ObjectUtils.isEmpty(orderId)) {
            //create case
        } else {
            ClientOrder order = orderRepository.findById(new BigInteger(orderId)).get();
            List<OrderParameter> parameters = order.getParameters();
            UIParameterFactory uiParameterFactory = new UIParameterFactory();
            List<String> htmlRows = uiParameterFactory.convertToRows(parameters);

            //show/edit case
        }

        model.addAttribute("title", "Home page");
        model.addAttribute("userName", "Stbe");
        return "order.html";
    }
}
