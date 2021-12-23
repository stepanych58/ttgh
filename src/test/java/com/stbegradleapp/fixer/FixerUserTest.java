package com.stbegradleapp.fixer;

import com.stbegradleapp.fixer.repositories.AttributeRepository;
import com.stbegradleapp.fixer.controllers.rest.UserRestController;
import com.stbegradleapp.fixer.repositories.OrderRepository;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import com.stbegradleapp.fixer.repositories.OrderParameterRepository;
import com.stbegradleapp.fixer.servises.UserService;
import com.stbegradleapp.fixer.storage.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserRestController.class)
public class FixerUserTest {
    @MockBean
    UserService userService;
    @MockBean
    FixerUserRepository userRepository;
    @MockBean
    AttributeRepository attributeRepository;
    @MockBean
    OrderParameterRepository orderParameterRepository;
    @MockBean
    OrderRepository orderRepository;
    @MockBean
    StorageService storageService;


    @Test
    public void getAllEngeeners() {
//        userService.getAllEngeeners()

    }

}
