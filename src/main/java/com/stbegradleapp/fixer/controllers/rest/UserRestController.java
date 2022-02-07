package com.stbegradleapp.fixer.controllers.rest;

import com.stbegradleapp.fixer.dto.UserDTO;
import com.stbegradleapp.fixer.dto.UserParameterDTO;
import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.OrderStatus;
import com.stbegradleapp.fixer.model.params.user.UserParameter;
import com.stbegradleapp.fixer.repositories.OrderRepository;
import com.stbegradleapp.fixer.servises.user.UserService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/fixer/api/user/")
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void setupMapper() {
        TypeMap<FixerUser, UserDTO> typeMap = modelMapper.createTypeMap(FixerUser.class, UserDTO.class);
        Converter<List<UserParameter>, List<UserParameterDTO>> collectionToSize = c -> c.getSource() != null ?
                c.getSource().stream()
                        .map(parameter ->
                                new UserParameterDTO(parameter.getName(), parameter.getType(), parameter.getValue()))
                        .collect(Collectors.toList()) : null;
        typeMap.addMappings(mapper ->
                mapper.using(collectionToSize).map(FixerUser::getParameters, UserDTO::setParameters)
        );
    }


    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public FixerUser create(@RequestBody FixerUser user) {
        validate(user);
        return user;
    }

    @GetMapping(path = "all")
    public List<UserDTO> all() {
//        return userService.getAllUsers();
        return null;
    }

    @GetMapping(path = "/role/{role}")
    public List<UserDTO> findByRole(@PathVariable("role") String role) {
//        UserRole userRole = UserRole.valueOf(role);
//        return userService.findByRole(userRole);
        return null;
    }

    @GetMapping(path = "/engineers")
    public List<UserDTO> engineers() {
//        Iterable<FixerUser> all = userService.getAllEngeeners();
//        System.out.println("all: " + all);
//        return (List<FixerUser>) all;
        return null;
    }

    @GetMapping(path = "/currentUser")
    public UserDTO currentUser(Authentication authentication) {
        if (authentication == null) return null;
        String userId = authentication.getName();
        AtomicReference<UserDTO> userDTO = new AtomicReference<>();
        userService.findByPhoneNumber(userId).ifPresent(
                user -> userDTO.set(modelMapper.map(user, UserDTO.class))
        );
        return userDTO.get();
    }

    @GetMapping(path = "/{id}")
    public FixerUser findById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/{id}/order/{orderId}")
    @ResponseBody
    public ClientOrder findUserOrder(@PathVariable("id") String id, @PathVariable("orderId") String orderId) {
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
    public Iterable<ClientOrder> getOrders(@PathVariable("userId") String userId) {
        Iterable<ClientOrder> allByClientId = orderRepository.findAllByClientId(new BigInteger(userId));
        return allByClientId;
    }

    @PostMapping("/{userId}")
    public ClientOrder saveOrUpdateOrder(@PathVariable("userId") String userId,
                                         @RequestBody ClientOrder order) {

        return orderRepository.save(new ClientOrder(order.getParameters(),
                userService.getUserById(userId),
                null,
                OrderStatus.OPEN));
    }

    //    @PatchMapping("/{userId}")
//    public ClientOrder updateOrder(@PathVariable("userId") String userId,
//                                         @RequestBody ClientOrder order){
//        order.setOrderForParams();
//        return orderRepository.save(order);
//    }
    @PatchMapping("/p")
    public ClientOrder updateUser(@Valid @RequestBody UserDTO userDTO) {
        String phoneNumber = userDTO.getPhoneNumber();
        if (!StringUtils.hasLength(phoneNumber)) {
            throw new RuntimeException("no phone number");
        }
        Optional<FixerUser> user = userService.findByPhoneNumber(phoneNumber);
        FixerUser fixerUser = user.map(u -> {
            if (StringUtils.hasLength(userDTO.getName())) {
                u.setName(userDTO.getName());
            }
            if (!CollectionUtils.isEmpty(userDTO.getParameters())) {
                userDTO.getParameters().stream().forEach(
                        p -> {
                            UserParameter parameterByName = u.getParameterByName(p.getName());
                            if (parameterByName != null) {
                                parameterByName.setValue(p.getValue());
                            }
                        }
                );
            }
            return u;
        }).orElseThrow(() -> new RuntimeException(" not found user: " + userDTO));
        userService.save(fixerUser);
//        orderRepository.save(order)
        return null;
    }

}
