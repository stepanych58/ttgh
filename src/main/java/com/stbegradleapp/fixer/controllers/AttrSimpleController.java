package com.stbegradleapp.fixer.controllers;

import com.stbegradleapp.fixer.model.params.AttrType;
import org.slf4j.Logger;
import com.stbegradleapp.fixer.model.params.OrderAttribute;
import com.stbegradleapp.fixer.repositories.FixerAttributeRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/attrs")
public class AttrSimpleController {
    @Autowired
    FixerAttributeRepository attributeRepository;
    private static final Logger logger = LoggerFactory.getLogger(AttrSimpleController.class);
    @GetMapping
    public String all(Model model) {
        logger.debug("test1");
        Iterable<OrderAttribute> attrs = attributeRepository.findAll();
        model.addAttribute("attrs", attrs);
        return "attrs/all";
    }

    @GetMapping("/{id}")
    public String getAttr(@PathVariable("id") String id, Model model) {
        Optional<OrderAttribute> attr = attributeRepository.findById(new BigInteger(id));
        model.addAttribute("attr", attr.get());
        return "attrs/attr";
    }
    @GetMapping("/new")
    public String creationPage(@ModelAttribute("attr") OrderAttribute attribute, Model model){
        model.addAttribute("attrTypes", AttrType.values());
        return "attrs/new";
    }

    @PostMapping()
    public String create(@RequestParam Map<String, String> req) {
        System.out.println("req: " + req);
        String name = req.get("name");
        String type = req.get("attrType");
        AttrType attrType = AttrType.valueOf(type);
        System.out.println("attrType: " + attrType);
        OrderAttribute attr = new OrderAttribute(name, attrType);
        if (attr.getName() != null){
            attributeRepository.save(attr);
        }
        return "redirect:/attrs";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") String id) {
        OrderAttribute attr = attributeRepository.findById(new BigInteger(id)).get();
        model.addAttribute("attrTypes", AttrType.values());
        model.addAttribute("attr", attr);
        return "attrs/edit";
    }

    @PatchMapping(value = "/{id}")
    public String update(/*@RequestBody OrderAttribute attr,*/ @PathVariable("id") String id, @RequestParam Map<String, String> req) {
//        System.out.println("stbeAtr: " + attr);
//        System.out.println("id: " + id);
        System.out.println("req: " + req);
        String name = req.get("name");
        String type = req.get("attrType");
        AttrType attrType = AttrType.valueOf(type);
        System.out.println("attrType: " + attrType);
        System.out.println("id: " + id);
        OrderAttribute attr = attributeRepository.findById(new BigInteger(id)).get();
        attr.setName(name);
        attr.setType(attrType);
        if (attr.getName() != null) {
            attributeRepository.save(attr);
        }
        return "redirect:/attrs";
    }


    @RequestMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        BigInteger attrId = new BigInteger(id);
        attributeRepository.deleteById(attrId);
        System.out.println("deleted by " + attrId);
        return "redirect:/attrs";
    }


}
