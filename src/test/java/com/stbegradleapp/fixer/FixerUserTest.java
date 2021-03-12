package com.stbegradleapp.fixer;

import com.stbegradleapp.fixer.controllers.rest.UserRestController;
import com.stbegradleapp.fixer.servises.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserRestController.class)
public class FixerUserTest {
    @MockBean
    UserService userService;


    @Test
    public void getAllEngeeners() {

    }

}
