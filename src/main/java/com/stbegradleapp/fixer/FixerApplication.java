package com.stbegradleapp.fixer;

import com.stbegradleapp.fixer.model.params.user.UserAttribute;
import com.stbegradleapp.fixer.model.params.user.UserParameter;
import com.stbegradleapp.fixer.repositories.OrderAttributeRepository;
import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.OrderStatus;
import com.stbegradleapp.fixer.model.params.user.UserRole;
import com.stbegradleapp.fixer.model.params.AttrType;
import com.stbegradleapp.fixer.model.params.order.ListValue;
import com.stbegradleapp.fixer.model.params.order.OrderAttribute;
import com.stbegradleapp.fixer.model.params.order.OrderParameter;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import com.stbegradleapp.fixer.repositories.OrderParameterRepository;
import com.stbegradleapp.fixer.repositories.OrderRepository;
import com.stbegradleapp.fixer.repositories.UserAttrRepository;
import com.stbegradleapp.fixer.repositories.UserParameterRepository;
import com.stbegradleapp.fixer.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class FixerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FixerApplication.class, args);
    }


    @Bean
    public CommandLineRunner testApp(FixerUserRepository userRepository,
                                     OrderAttributeRepository attributeRepository,
                                     OrderParameterRepository parameterRepository,
                                     OrderRepository clientOrderRepository,
                                     StorageService storageService,
                                     PasswordEncoder passwordEncoder,
                                     UserAttrRepository userAttrRepository,
                                     UserParameterRepository userParameterRepository
    ) {
        return args -> {
            storageService.deleteAll();
            storageService.init();
            createTestOrderAttrs(attributeRepository);
            createTestUserAttrs(userAttrRepository);
//sdsfdfs
//sdsfdfs
//sdsfdfs
//sdsfdfs
//sdsfdfs
            FixerUser salavat = new FixerUser("Салават", passwordEncoder.encode("111"), "89279874356", UserRole.ROLE_ADMIN);
            FixerUser ksusha = new FixerUser("Ксюша", passwordEncoder.encode("111"), "89279874355", UserRole.ROLE_ADMIN);
            userRepository.saveAll(List.of(salavat, ksusha));

            FixerUser genadii = new FixerUser("Генадий", passwordEncoder.encode("111"), "89275785698", UserRole.ENGINEER);
            Map<String, String> genadiiParams =
                    getUserParams("gena@yandex.ru", "Москва, ул. Кутузова д. 17б, кв 14", "Журавлев");
            createUser(userAttrRepository, userParameterRepository, userRepository, genadii, genadiiParams);

            FixerUser dima = new FixerUser("Дима", passwordEncoder.encode("111"), "89275785699", UserRole.ENGINEER);
            Map<String, String> dimaParams =
                    getUserParams("dima@yandex.ru", "Москва, ул. Новоогорева д. 1733б, кв 14", "Саленый");
            createUser(userAttrRepository, userParameterRepository, userRepository, dima, dimaParams);

            FixerUser sveta = new FixerUser("Света", "Хорошева", passwordEncoder.encode("111"),
                        "89276976454", UserRole.CLIENT);
            Map<String, String> svetaParams =
                    getUserParams("sveta@yandex.ru", "Астрахань, ул. Чапаева д. 18б, кв 123", "Хорошева");
            createUser(userAttrRepository, userParameterRepository, userRepository, sveta, svetaParams);

            createClient(clientOrderRepository, attributeRepository,
                    genadii, sveta,
                    getOrderParams("24/09/2020", "сломался холодильник", "холодильник", "photo"));

            FixerUser emma = new FixerUser("Эмма", passwordEncoder.encode("111"), "89276976455", UserRole.CLIENT);
            Map<String, String> emmaParams =
                    getUserParams("emma@yandex.ru", "Москва, ул. Студеный проезт д. 18б, кв 123", "Гаджиева");
            createUser(userAttrRepository, userParameterRepository, userRepository, emma, emmaParams);

            createClient(clientOrderRepository, attributeRepository,
                    dima, emma,
                    getOrderParams("24/09/2020", "сломалась газовая плита", "Газовая плита", "photo"));

            FixerUser sergei = new FixerUser("Сергей", passwordEncoder.encode("111"), "89276976456", UserRole.CLIENT);
            Map<String, String> sergeiParams =
                    getUserParams("sergei@yandex.ru", "Самара, ул. Новосадовая  д. 16б, кв 12", "Сергеев");
            createUser(userAttrRepository, userParameterRepository, userRepository, sergei, sergeiParams);

            createClient(clientOrderRepository, attributeRepository,
                    dima, emma,
                    getOrderParams("24/09/2020", "сломался Телевизор", "Телевизор", "photo"));

            OrderAttribute time = new OrderAttribute("Время", AttrType.TIME);

            attributeRepository.save(time);
            System.out.println("TEST DATA CREATED");
        };
    }



    private Map<String, String> getOrderParams(String attr1,
                                               String attr2,
                                               String attr3,
                                               String attr4) {
        Map<String, String> params = new HashMap<>();
        params.put("Дата", attr1);
        params.put("Напишите что произошло", attr2);
        params.put("Что чиним", attr3);
        params.put("Добавьте фото", attr4);
        return params;
    }

    private void createClient(
            OrderRepository clientOrderRepository,
            OrderAttributeRepository attributeRepository,
            FixerUser engineer,
            FixerUser client,
            Map<String, String> params
    ) {
        Iterator<String> iterator = params.keySet().iterator();
        String next = iterator.next();
        OrderParameter dateParam = new OrderParameter(attributeRepository.findByName(next), params.get(next));
        next = iterator.next();
        OrderParameter descriptionParam = new OrderParameter(attributeRepository.findByName(next), params.get(next));
        next = iterator.next();
        OrderParameter eqParam = new OrderParameter(attributeRepository.findByName(next), params.get(next));
        next = iterator.next();
        OrderParameter photoParam = new OrderParameter(attributeRepository.findByName(next), params.get(next));
        ClientOrder svetlanaOrder = new ClientOrder(
                List.of(dateParam, descriptionParam, photoParam, eqParam),
                client,
                engineer,
                OrderStatus.OPEN);

        clientOrderRepository.save(svetlanaOrder);
    }

    private void createUser(
            UserAttrRepository attributeRepository,
            UserParameterRepository userParameterRepository,
            FixerUserRepository userRepository,
            FixerUser user,
            Map<String, String> params
    ) {
        userRepository.save(user);
        Iterator<String> iterator = params.keySet().iterator();

        while (iterator.hasNext() ) {
            String next = iterator.next();
            userParameterRepository.save(new UserParameter(attributeRepository.findByName(next), params.get(next), user));
        }
//        UserParameter p1 = new UserParameter(attributeRepository.findByName(next), params.get(next), user);
//        next = iterator.next();
//        UserParameter p2 = new UserParameter(attributeRepository.findByName(next), params.get(next), user);
//        next = iterator.next();
//        UserParameter p3 = new UserParameter(attributeRepository.findByName(next), params.get(next), user);
//        userParameterRepository.saveAll(List.of(p1, p2, p3));
    }

    private Map<String, String> getUserParams(String email, String address, String secondName) {
        Map<String, String> genadiiParams = new HashMap<>();
        genadiiParams.put("Почта", email);
        genadiiParams.put("Адрес", address);
//        genadiiParams.put("Second Name", secondName);
        return genadiiParams;
    }

    private void createTestUserAttrs(UserAttrRepository userAttrRepository) {
        List<UserAttribute> attrs = List.of(
                new UserAttribute("Почта", AttrType.EMAIL),
                new UserAttribute("Адрес", AttrType.ADDRESS)
//                new UserAttribute("Second Name", AttrType.TEXT)
        );
        userAttrRepository.saveAll(attrs);
    }

    private void createTestOrderAttrs(OrderAttributeRepository attributeRepository) {
        ListValue fridge = new ListValue("Холодильник");
        ListValue tv = new ListValue("Телевизор");
        ListValue washingMachine = new ListValue("Стиральная Машинка");
        ListValue stove = new ListValue("Газовая плита");
        List<ListValue> equipments = List.of(fridge, tv, washingMachine, stove);
        OrderAttribute equipment = new OrderAttribute("Что чиним", AttrType.LIST, equipments);
        OrderAttribute description = new OrderAttribute("Напишите что произошло", AttrType.TEXT);
        OrderAttribute photo = new OrderAttribute("Добавьте фото", AttrType.TEXT);
        OrderAttribute dateAttr = new OrderAttribute("Дата", AttrType.DATE);
        OrderAttribute clientName = new OrderAttribute("Ваше Имя", AttrType.TEXT);
        OrderAttribute phoneNumber = new OrderAttribute("Ваш номер телефона", AttrType.PHONE_NUMBER);
        OrderAttribute address = new OrderAttribute("Ваш Адрес", AttrType.ADDRESS);

        List<OrderAttribute> listAttrsThatShouldBeInSystem = List.of(
                equipment, description, photo, dateAttr, clientName, phoneNumber, address
        );
        attributeRepository.saveAll(listAttrsThatShouldBeInSystem);
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/fixer/api/attrs").allowedOrigins("http://localhost:3000");
//            }
//        };
//    }
}
