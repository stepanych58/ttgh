package com.stbegradleapp.fixer;

import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.UserRole;
import com.stbegradleapp.fixer.model.params.AttrType;
import com.stbegradleapp.fixer.model.params.ListValue;
import com.stbegradleapp.fixer.model.params.OrderAttribute;
import com.stbegradleapp.fixer.model.params.OrderParameter;
import com.stbegradleapp.fixer.repositories.AttributeRepository;
import com.stbegradleapp.fixer.repositories.ClientOrderRepository;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import com.stbegradleapp.fixer.repositories.OrderParameterRepository;
import com.stbegradleapp.fixer.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class FixerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FixerApplication.class, args);
    }

    @Bean
    public CommandLineRunner testApp(FixerUserRepository userRepository,
                                     AttributeRepository attributeRepository,
                                     OrderParameterRepository parameterRepository,
                                     ClientOrderRepository clientOrderRepository,
                                     StorageService storageService
    ) {
        return args -> {
            storageService.deleteAll();
            storageService.init();


            userRepository.save(new FixerUser("Salavat", "89279874356", UserRole.ADMIN));
            FixerUser sveta = new FixerUser("Sveta", "89279874233", UserRole.CLIENT);
            userRepository.save(sveta);
            FixerUser genadii = new FixerUser("Genadii", "89275785698", UserRole.ENGINEER);
            userRepository.save(genadii);

            ListValue fridge = new ListValue("Холодильник");
            ListValue tv = new ListValue("Телевизор");
            ListValue washingMachine = new ListValue("Стиральная Машинка");
            ListValue stove = new ListValue("Газовая плита");
            List<ListValue> equipments = new LinkedList<ListValue>();
            equipments.add(fridge);
            equipments.add(tv);
            equipments.add(washingMachine);
            equipments.add(stove);
            OrderAttribute equipment = new OrderAttribute("Что чиним", AttrType.LIST, equipments);

            OrderAttribute description = new OrderAttribute("Напишите что произошло", AttrType.TEXT);
            OrderAttribute photo = new OrderAttribute("Добавьте фото", AttrType.DATA);
            OrderAttribute dateAttr = new OrderAttribute("Когда приехать", AttrType.DATE);
//            OrderAttribute time = new OrderAttribute("Во сколько", AttrType.TIME);
            OrderAttribute clientName = new OrderAttribute("Ваше Имя", AttrType.TEXT);
            OrderAttribute phoneNumber = new OrderAttribute("Ваш номер телефона", AttrType.PHONE_NUMBER);
            OrderAttribute address = new OrderAttribute("Ваш Адрес", AttrType.ADDRESS);
            List<OrderAttribute> listAttrsThatShouldBeInSystem = Arrays.asList(
                    equipment, description, photo, dateAttr, clientName, phoneNumber,address
            );
            OrderParameter dateParam = new OrderParameter(dateAttr, "24/09/2020");
            OrderParameter descriptionParam = new OrderParameter(description, "сломался холодильник");
            OrderParameter eqParam = new OrderParameter(equipment, "холодильник");
            OrderParameter photoParam = new OrderParameter(photo, "photo");
            ClientOrder svetlanaOrder = new ClientOrder(
                    Arrays.asList(dateParam, descriptionParam, photoParam, eqParam),
                    sveta,
                    genadii);

            attributeRepository.saveAll(listAttrsThatShouldBeInSystem);
            clientOrderRepository.save(svetlanaOrder);
            Iterable<FixerUser> all = userRepository.findAll();
            System.out.println(String.format("all: %S", all));

        };
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/fixer/api/attrs").allowedOrigins("http://localhost:3000");
            }
        };
    }
}
