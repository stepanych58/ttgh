package com.stbegradleapp.fixer.controllers;

import com.stbegradleapp.fixer.FixerConstants;
import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.params.OrderAttribute;
import com.stbegradleapp.fixer.model.params.OrderParameter;
import com.stbegradleapp.fixer.repositories.AttributeRepository;
import com.stbegradleapp.fixer.repositories.ClientOrderRepository;
import com.stbegradleapp.fixer.repositories.OrderParameterRepository;
import com.stbegradleapp.fixer.ui.UIParameterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    AttributeRepository attributeRepository;
    @Autowired
    OrderParameterRepository parameterRepository;
    @Autowired
    ClientOrderRepository clientOrderRepository;

    @GetMapping(path = "/")
    public String home(Model model)
    {
        model.addAttribute("title", "Home page");
        model.addAttribute("name", "Stbe");
        return "website";
    }

    @PostMapping(path = "/login")
    public String login(Model model, @RequestParam Map<String,String> allParams)
    {
        System.out.println("allParams: " + allParams);
        model.addAttribute("title", "Home page");
        String userName = allParams.get("userName");
        model.addAttribute("name", ( userName == null ? "Stbe": userName));
        return "/lk/index";
    }
    @PostMapping(path = "/order/")
    public String orderPage(Model model, @RequestParam Map<String,String> allParams)
    {
        String orderId = allParams.get(FixerConstants.ORDER_ID);
        Iterable<OrderAttribute> attributes = attributeRepository.findAll();
        if (ObjectUtils.isEmpty(orderId)) {
            //create case
        } else {
            ClientOrder order = clientOrderRepository.findById(new BigInteger(orderId)).get();
            List<OrderParameter> parameters = order.getParameters();
            UIParameterFactory uiParameterFactory = new UIParameterFactory();
            List<String> htmlRows = uiParameterFactory.convertToRows(parameters);

            //show/edit case
        }

        model.addAttribute("title", "Home page");
        model.addAttribute("userName", "Stbe");
        return "order.html";
    }


    @PostMapping(path = "/createOrder")
    public String createOrder(Model model, @RequestParam Map<String,String> allParams){
        return "order";
    }
}
