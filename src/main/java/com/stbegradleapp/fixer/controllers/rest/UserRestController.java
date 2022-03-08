package com.stbegradleapp.fixer.controllers.rest;

import com.stbegradleapp.fixer.dto.*;
import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.OrderStatus;
import com.stbegradleapp.fixer.model.params.user.UserAttribute;
import com.stbegradleapp.fixer.model.params.user.UserParameter;
import com.stbegradleapp.fixer.repositories.OrderRepository;
import com.stbegradleapp.fixer.repositories.UserAttrRepository;
import com.stbegradleapp.fixer.servises.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/fixer/api/user/")
public class UserRestController {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final UserAttrRepository userAttrRepository;

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void setupMapper() {
        TypeMap<FixerUser, UserDTO> typeMap = modelMapper.createTypeMap(FixerUser.class, UserDTO.class);
        Converter<List<UserParameter>, List<ParameterDTO>> collectionToSize = c -> c.getSource() != null ?
            c.getSource().stream()
                .map(parameter ->
                    new ParameterDTO(parameter.getName(), parameter.getAttrId(), parameter.getType(), parameter.getValue()))
                .collect(Collectors.toList()) : null;
        typeMap.addMappings(mapper ->
            mapper.using(collectionToSize).map(FixerUser::getParameters, UserDTO::setParameters)
        );

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

    @GetMapping("/orders")
    public Iterable<ClientOrderDTO> getOrders(Authentication auth) {
        String userTN = auth.getName();
        Optional<FixerUser> user = userService.findByPhoneNumber(userTN);
        List<ClientOrder> orders = user.map(u -> u.getOrders()).orElseThrow(() -> new RuntimeException("error"));
        List<ClientOrderDTO> result = new ArrayList<>();
        orders.forEach(o -> result.add(modelMapper.map(o, ClientOrderDTO.class)));
        return result;
    }

    @GetMapping("/model")
    public Iterable<AttrDTO> getUserModel(Authentication auth) {
        Iterable<UserAttribute> attrs = userService.getUserModel();
        Collection<AttrDTO> result = new ArrayList<>();
        attrs.forEach(o -> result.add(modelMapper.map(o, AttrDTO.class)));
        return result;
    }

    @PostMapping("/{userId}")
    public ClientOrder saveOrUpdateOrder(@PathVariable("userId") String userId,
                                         @RequestBody ClientOrder order) {

        return orderRepository.save(new ClientOrder(order.getParameters(),
            userService.getUserById(userId),
            null,
            OrderStatus.OPEN));
    }

    @PatchMapping("/update")
    public UserDTO updateUser(@Valid @RequestBody UserDTO userDTO, Authentication auth) {
        String phoneNumber = auth.getName();
        if (!StringUtils.hasLength(phoneNumber)) {
            throw new RuntimeException("no phone number");
        }
        Optional<FixerUser> user = userService.findByPhoneNumber(phoneNumber);
        FixerUser fixerUser = user.map(u -> {
            if (StringUtils.hasLength(userDTO.getFirstName())) {
                u.setFirstName(userDTO.getFirstName());
            }
            if (StringUtils.hasLength(userDTO.getSecondName())) {
                u.setSecondName(userDTO.getSecondName());
            }
            if (StringUtils.hasLength(userDTO.getPhoneNumber())) {
                u.setPhoneNumber(userDTO.getPhoneNumber());
            }

            if (!CollectionUtils.isEmpty(userDTO.getParameters())) {
                List<UserParameter> userParams = userDTO.getParameters().stream().map(
                    p -> {
                        UserParameter parameterByName = u.getParameterByName(p.getName());
                        if (parameterByName != null) {
                            parameterByName.setValue(p.getValue());

                        } else {
                            Optional<UserAttribute> attribute = userAttrRepository.findById(p.getAttrId());
                            parameterByName = attribute.map(a -> new UserParameter(a, p.getValue(), u)).orElseThrow(() -> {
                                throw new RuntimeException("no attr by :" + p.getAttrId());
                            });
                        }
                        return parameterByName;
                    }
                ).collect(Collectors.toList());
                u.setParameters(userParams);
            }
            return u;
        }).orElseThrow(() -> new RuntimeException(" not found user: " + userDTO));
        FixerUser save = userService.save(fixerUser);
        return modelMapper.map(save, UserDTO.class);
    }
}
