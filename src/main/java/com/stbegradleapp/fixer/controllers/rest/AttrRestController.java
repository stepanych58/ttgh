package com.stbegradleapp.fixer.controllers.rest;

import com.stbegradleapp.fixer.model.params.AttrDTO;
import com.stbegradleapp.fixer.model.params.AttrType;
import com.stbegradleapp.fixer.model.params.ListValue;
import com.stbegradleapp.fixer.model.params.OrderAttribute;
import com.stbegradleapp.fixer.repositories.AttributeRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RestController
@RequestMapping("/fixer/api/attrs")
@Slf4j
public class AttrRestController {

    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void setupMapper() {
        TypeMap<OrderAttribute, AttrDTO> typeMap = modelMapper.createTypeMap(OrderAttribute.class, AttrDTO.class);
        Converter<Collection<ListValue>, List<String>> collectionToSize = c -> c.getSource() != null ?
                c.getSource().stream().map(lv -> lv.getValue()).collect(Collectors.toList()) : null;
        typeMap.addMappings(mapper ->
                mapper.using(collectionToSize).map(OrderAttribute::getListValues, AttrDTO::setListValues)
        );
    }

    @GetMapping
    public List<AttrDTO> all() {
        List<AttrDTO> result = new ArrayList<>();
        attributeRepository.findAll().forEach(attr->result.add(convertToDTO(attr)));
        return result;
    }

//    @RequestMapping(params = {"id"})
//    public AttrDTO getById(@RequestParam("id") String id) {
    @RequestMapping(path = {"/{id}"})
    public AttrDTO getById(@PathVariable("id") String id) {
        Optional<OrderAttribute> attr = attributeRepository.findById(new BigInteger(id));
        AttrDTO result = attr.isPresent() ? convertToDTO(attr.get()) : null;
        log.debug("res: {}", result);
        return result;
    }

    private AttrDTO convertToDTO(OrderAttribute orderAttribute) {
        return modelMapper.map(orderAttribute, AttrDTO.class);
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
