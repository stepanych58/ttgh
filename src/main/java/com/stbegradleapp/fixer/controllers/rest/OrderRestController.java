package com.stbegradleapp.fixer.controllers.rest;

import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.UserRole;
import com.stbegradleapp.fixer.model.params.OrderAttribute;
import com.stbegradleapp.fixer.model.params.OrderParameter;
import com.stbegradleapp.fixer.repositories.ClientOrderRepository;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import com.stbegradleapp.fixer.repositories.OrderParameterRepository;
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
    private ClientOrderRepository orderRepository;
    @Autowired
    private FixerUserRepository userRepository;

    @GetMapping(path = "{id}")
    public ClientOrder getOrder(@PathVariable("id") String id) {
        BigInteger id1 = new BigInteger(id);
        System.out.println("id1: = " + id1);
        System.out.println("id: = " + id);
        Optional<ClientOrder> clientOrder = orderRepository.findById(id1);
        System.out.println(String.format("client %s", clientOrder));
        return clientOrder.get();
    }

    @GetMapping(path = "all")
    public Iterable<ClientOrder> all() {
        return orderRepository.findAll();
    }

    @RequestMapping(value = "/{orderId}/assign/{engeenerId}")
    @ResponseBody
    public ClientOrder assignEngeenerToOrder(@PathVariable("orderId") String orderId,
                                     @PathVariable("engeenerId") String engeenerId) {
        System.out.println("-----ASSIGN ENGEENER METHOD CALL START");
        System.out.println("-----USERID: " + orderId);
        System.out.println("-----ENGEENERID: " + engeenerId);
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
        System.out.println("-----ASSIGN ENGEENER METHOD CALL END");
        return order;

    }

    @RequestMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientOrder create(@RequestBody ClientOrder order){
        List<OrderParameter> parameters = order.getParameters();
        if (parameters != null){
            FixerUser savedUser = userRepository.save(order.getClient());
//            order.initParameters();
//            ClientOrder svetlanaOrder = new ClientOrder(
//                    order.getParameters(),
//                    savedUser,
//                    null);
            System.out.println(order);
            ClientOrder resOrder = orderRepository.save(order);
//            order.setClient(savedUser);
//            for (OrderParameter parameter: parameters){
//                parameter.setOrder(order);
//            }
//            Iterable<OrderParameter> orderParameters = parameterRepository.saveAll(parameters);
//
//            order.setParameters((List<OrderParameter>) orderParameters);
            return resOrder;
        }
        return order;
    }


}
