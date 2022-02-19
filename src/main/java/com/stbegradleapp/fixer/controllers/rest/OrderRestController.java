package com.stbegradleapp.fixer.controllers.rest;

import com.stbegradleapp.fixer.dto.ClientOrderDTO;
import com.stbegradleapp.fixer.dto.OrderParameterDTO;
import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.OrderStatus;
import com.stbegradleapp.fixer.model.params.user.UserRole;
import com.stbegradleapp.fixer.model.params.order.OrderParameter;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import com.stbegradleapp.fixer.repositories.OrderAttributeRepository;
import com.stbegradleapp.fixer.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/fixer/api/order/")
@Slf4j
public class OrderRestController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FixerUserRepository userRepository;
    @Autowired
    private OrderAttributeRepository orderAttributeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void setupMapper() {
        TypeMap<ClientOrder, ClientOrderDTO> typeMap = modelMapper.createTypeMap(ClientOrder.class, ClientOrderDTO.class);
        TypeMap<ClientOrderDTO, ClientOrder> typeMap2 = modelMapper.createTypeMap(ClientOrderDTO.class, ClientOrder.class);
        Converter<List<OrderParameter>, List<OrderParameterDTO>> collectionToSize = c -> c.getSource() != null ?
                c.getSource().stream()
                        .map(parameter ->
                                new OrderParameterDTO(parameter.getName(), parameter.getAttribute().getId(),
                                        parameter.getType(), parameter.getValue()))
                        .collect(Collectors.toList()) : null;
        typeMap.addMappings(mapper ->
                mapper.using(collectionToSize).map(ClientOrder::getParameters, ClientOrderDTO::setParameters)
        );

        Converter<List<OrderParameterDTO>, List<OrderParameter>> collectionToSize2 = c -> c.getSource() != null ?
                c.getSource().stream()
                        .map(parameter ->
                                new OrderParameter(orderAttributeRepository.findById(parameter.getAttrId()).get(),
                                        parameter.getValue()))
                        .collect(Collectors.toList()) : null;
        typeMap2.addMappings(mapper ->
                mapper.using(collectionToSize2).map(ClientOrderDTO::getParameters, ClientOrder::setParameters)
        );
        typeMap.addMappings(
                mapper -> mapper.map(src -> src.getClient().getPhoneNumber(), ClientOrderDTO::setClient)
        );
        typeMap.addMappings(
                mapper -> mapper.map(src -> src.getEngineer().getPhoneNumber(), ClientOrderDTO::setExecutor)
        );

    }


    @GetMapping(path = "{id}")
    public ClientOrder getOrder(@PathVariable("id") String id) {
        BigInteger orderId = new BigInteger(id);
        Optional<ClientOrder> clientOrder = orderRepository.findById(orderId);
        return clientOrder.get();
    }

    @GetMapping(path = "all")
    public Collection<ClientOrderDTO> all() {
        Iterable<ClientOrder> all = orderRepository.findAll();
        List<ClientOrderDTO> result = new ArrayList<>();
        all.forEach( order -> {
                    result.add(modelMapper.map(order, ClientOrderDTO.class));
                }
        );
        return result;
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
    public ClientOrderDTO create(@RequestBody ClientOrderDTO order, Authentication auth){
        String clientPhone = order.getClient();
        log.info("order/create");
        if (ObjectUtils.isEmpty(clientPhone) && auth != null) {
            clientPhone = auth.getName();
            log.info("clientPhone {}", clientPhone);
        }
        String executorPhone = order.getExecutor();
        Optional<FixerUser> clientObj = userRepository.findByPhoneNumber(clientPhone);
        Optional<FixerUser> executorObj = userRepository.findByPhoneNumber(executorPhone);

        ClientOrder toSave = modelMapper.map(order, ClientOrder.class);
        FixerUser client = clientObj.orElseThrow(() -> new RuntimeException("no client"));
        FixerUser executor = executorObj.orElseThrow(() -> new RuntimeException("no executor"));
        toSave.setClient(client);
        toSave.setEngineer(executor);

        orderRepository.save(toSave);
        return order;
    }

    @DeleteMapping(path = "/delete/{orderId}")
    public void deleteOrder(@PathVariable("orderId") BigInteger orderId) {
        orderRepository.deleteById(orderId);
    }


}
