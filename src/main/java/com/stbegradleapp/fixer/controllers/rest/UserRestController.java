package com.stbegradleapp.fixer.controllers.rest;

import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/fixer/api/user/")
public class UserRestController {
    @Autowired
    private FixerUserRepository fixerUserRepository;

    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public FixerUser create(@RequestBody FixerUser user) {
        validate(user);
        return user;
    }

    @GetMapping
    public List<FixerUser> all(){
        Iterable<FixerUser> all = fixerUserRepository.findAll();
        System.out.println("all: " + all);
        return (List<FixerUser>) all;
    }

    @GetMapping(path = "/{id}")
    public FixerUser findById(@PathVariable("id") String id){
        BigInteger userId = new BigInteger(id);
        Optional<FixerUser> user = fixerUserRepository.findById(userId);
        System.out.println("user: " + user.get());
        return  user.get();
    }

    @RequestMapping(value = "/{id}/order/{orderId}")
    @ResponseBody
    public ClientOrder findOrderByUserAndOrderId(@PathVariable("id") String id, @PathVariable("orderId") String orderId){
        BigInteger userId = new BigInteger(id);
        FixerUser user = fixerUserRepository.findById(userId).get();
        List<ClientOrder> orders = user.getOrders();
        Stream<ClientOrder> clientOrderStream = orders.stream().filter(o -> o.getId().equals(new BigInteger(orderId)));
        System.out.println("user: " + user);
        ClientOrder clientOrder = clientOrderStream.findFirst().get();
        System.out.println("order: " + clientOrder);
        return clientOrder;
    }


    private void validate(FixerUser user) {
        System.out.println("validate start fixer user: " + user);
    }

}
