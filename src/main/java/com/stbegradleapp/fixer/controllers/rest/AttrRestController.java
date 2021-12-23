package com.stbegradleapp.fixer.controllers.rest;

import com.stbegradleapp.fixer.model.params.OrderAttribute;
import com.stbegradleapp.fixer.repositories.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RestController
@RequestMapping("/fixer/api/attrs")
public class AttrRestController {

    @Autowired
    AttributeRepository attributeRepository;

    @RequestMapping
    public Iterable<OrderAttribute> showAll(){
        Iterable all = attributeRepository.findAll();
        System.out.println(all);
        return all;
    }

    @RequestMapping(path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderAttribute create(@RequestBody OrderAttribute attribute) {
        System.out.println(attribute);
        if (attribute.getName() != null) {
            OrderAttribute savedAttr = attributeRepository.save(attribute);
            return savedAttr;
        }
        return attribute;
    }
}
