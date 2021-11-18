package com.stbegradleapp.fixer.controllers.rest;

import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.OrderStatus;
import com.stbegradleapp.fixer.model.UserRole;
import com.stbegradleapp.fixer.repositories.OrderRepository;
import com.stbegradleapp.fixer.servises.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/fixer/api/user/")
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public FixerUser create(@RequestBody FixerUser user) {
        validate(user);
        return user;
    }

    @GetMapping
    public List<FixerUser> all() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/role/{role}")
    public Iterable<FixerUser> findByRole(@PathVariable("role") String role) {
        UserRole userRole = UserRole.valueOf(role);
        return userService.findByRole(userRole);
    }

    @GetMapping(path = "/engeeners")
    public List<FixerUser> engeeners() {
        Iterable<FixerUser> all = userService.getAllEngeeners();
        System.out.println("all: " + all);
        return (List<FixerUser>) all;
    }

    @GetMapping(path = "/currentUser")
    public FixerUser currentUser(Authentication authentication) {
        if (authentication==null) return null;
        String userId = authentication.getName();
        return userService.getUserById(userId);
    }

    @GetMapping(path = "/{id}")
    public FixerUser findById(@PathVariable("id") String id){
        return  userService.getUserById(id);
    }

    @RequestMapping(value = "/{id}/order/{orderId}")
    @ResponseBody
    public ClientOrder findUserOrder(@PathVariable("id") String id, @PathVariable("orderId") String orderId){
        FixerUser user = userService.getUserById(id);
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

    @GetMapping("/{userId}/orders")
    public Iterable<ClientOrder> getOrders(@PathVariable("userId") String userId){
        Iterable<ClientOrder> allByClientId = orderRepository.findAllByClientId(new BigInteger(userId));
        return allByClientId;
    }

    @PostMapping("/{userId}")
    public ClientOrder saveOrUpdateOrder(@PathVariable("userId") String userId,
                                         @RequestBody ClientOrder order){

        return orderRepository.save(new ClientOrder(order.getParameters(),
                userService.getUserById(userId),
                null,
                OrderStatus.OPEN));
    }

    @PatchMapping("/{userId}")
    public ClientOrder updateOrder(@PathVariable("userId") String userId,
                                         @RequestBody ClientOrder order){
        return orderRepository.save(order);
    }

}
