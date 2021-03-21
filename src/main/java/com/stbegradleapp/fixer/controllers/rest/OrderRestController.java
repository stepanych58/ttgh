package com.stbegradleapp.fixer.controllers.rest;

import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.OrderStatus;
import com.stbegradleapp.fixer.model.UserRole;
import com.stbegradleapp.fixer.model.params.OrderParameter;
import com.stbegradleapp.fixer.repositories.OrderRepository;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fixer/api/order/")
public class OrderRestController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FixerUserRepository userRepository;

    @GetMapping(path = "{id}")
    public ClientOrder getOrder(@PathVariable("id") String id) {
        BigInteger orderId = new BigInteger(id);
        Optional<ClientOrder> clientOrder = orderRepository.findById(orderId);
        return clientOrder.get();
    }

    @GetMapping(path = "all")
    public Iterable<ClientOrder> all() {
        return orderRepository.findAll();
    }

    @GetMapping(path = "status/{status}")
    public Iterable<ClientOrder> showOrdersByStatus(@PathVariable("status") String status) {
        return orderRepository.findAllByStatus(OrderStatus.valueOf(status));
    }

    @RequestMapping(value = "/{orderId}/assign/{engeenerId}")
    @ResponseBody
    public ClientOrder assignEngeenerToOrder(@PathVariable("orderId") String orderId,
                                     @PathVariable("engeenerId") String engeenerId) {
        if (ObjectUtils.isEmpty(orderId) ||
                ObjectUtils.isEmpty(engeenerId)) {
            throw new RuntimeException("incorrect request data");
        }
        ClientOrder order = orderRepository.findById(new BigInteger(orderId)).orElseThrow(
                ()->new RuntimeException("No order by id" + orderId)
        );
        FixerUser enginer = userRepository.findById(new BigInteger(engeenerId)).orElseThrow(
                ()->new RuntimeException("no User by id" + engeenerId)
        );
        if (!UserRole.ENGINEER.equals(enginer.getRole())) {
            throw new RuntimeException("User not engeener");
        }
        order.setEngineer(enginer);
        orderRepository.save(order);
        return order;
    }

    @RequestMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientOrder create(@RequestBody ClientOrder order){
        List<OrderParameter> parameters = order.getParameters();
        if (parameters != null){
            ClientOrder resOrder = orderRepository.save(order);
            return resOrder;
        }
        return order;
    }


}
