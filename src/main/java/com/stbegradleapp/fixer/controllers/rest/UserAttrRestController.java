package com.stbegradleapp.fixer.controllers.rest;

import com.stbegradleapp.fixer.dto.AttrDTO;
import com.stbegradleapp.fixer.dto.UserAttrDTO;
import com.stbegradleapp.fixer.model.params.order.ListValue;
import com.stbegradleapp.fixer.model.params.order.OrderAttribute;
import com.stbegradleapp.fixer.model.params.user.UserAttribute;
import com.stbegradleapp.fixer.repositories.OrderAttributeRepository;
import com.stbegradleapp.fixer.repositories.UserAttrRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/fixer/api/user/attrs")
@Slf4j
public class UserAttrRestController {

    private UserAttrRepository attributeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserAttrRestController(UserAttrRepository attributeRepository, ModelMapper modelMapper) {
        this.attributeRepository = attributeRepository;
        this.modelMapper = modelMapper;
    }

//    @PostConstruct
//    public void setupMapper() {
//        TypeMap<OrderAttribute, UserAttrDTO> typeMap = modelMapper.createTypeMap(OrderAttribute.class, UserAttrDTO.class);
//        Converter<Collection<ListValue>, List<String>> collectionToSize = c -> c.getSource() != null ?
//                c.getSource().stream().map(lv -> lv.getValue()).collect(Collectors.toList()) : null;
//        typeMap.addMappings(mapper ->
//                mapper.using(collectionToSize).map(UserAttribute::getListValues, UserAttrDTO::setListValues)
//        );
//    }

    @GetMapping("/all")
    public List<UserAttrDTO> all() {
        List<UserAttrDTO> result = new ArrayList<>();
        attributeRepository.findAll().forEach(attr->result.add(convertToDTO(attr)));
        return result;
    }

//    @RequestMapping(params = {"id"})
//    public AttrDTO getById(@RequestParam("id") String id) {
    @RequestMapping(path = {"/{id}"})
    public UserAttrDTO getById(@PathVariable("id") String id) {
        Optional<UserAttribute> attr = attributeRepository.findById(new BigInteger(id));
        UserAttrDTO result = attr.isPresent() ? convertToDTO(attr.get()) : null;
        log.debug("res: {}", result);
        return result;
    }

    private UserAttrDTO convertToDTO(UserAttribute attribute) {
        return modelMapper.map(attribute, UserAttrDTO.class);
    }

    @RequestMapping(path = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserAttribute create(@RequestBody UserAttribute attribute) {
        System.out.println(attribute);
        if (attribute.getName() != null) {
            UserAttribute savedAttr = attributeRepository.save(attribute);
            return savedAttr;
        }
        return attribute;
    }
}
