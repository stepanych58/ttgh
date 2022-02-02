package com.stbegradleapp.fixer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stbegradleapp.fixer.repositories.OrderAttributeRepository;
import com.stbegradleapp.fixer.controllers.rest.OrderRestController;
import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.UserRole;
import com.stbegradleapp.fixer.repositories.OrderRepository;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import com.stbegradleapp.fixer.repositories.OrderParameterRepository;
import com.stbegradleapp.fixer.servises.user.UserService;
import com.stbegradleapp.fixer.storage.StorageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(OrderRestController.class)
public class OrderServiceTest {

    @Autowired
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    UserService userService;
    @MockBean
    FixerUserRepository userRepository;
    @MockBean
    OrderAttributeRepository attributeRepository;
    @MockBean
    OrderParameterRepository orderParameterRepository;
    @MockBean
    StorageService storageService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
//    @Autowired
//    private Filter springSecurityFilterChain;
    @Test
    public void testListOrders() throws Exception {
        FixerUser user = new FixerUser("stbe", "9999", UserRole.CLIENT);
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClient(user);
        Iterable<ClientOrder> allOrders = List.of(clientOrder);
        Mockito.when(orderRepository.findAll()).thenReturn(allOrders);
        String endPoint = "/fixer/api/order/all";

        MvcResult mvcResult = mockMvc.perform(get(endPoint))
                .andExpect(status().isOk())
                .andReturn();
        String actualResult = mvcResult.getResponse().getContentAsString();
        String expectedResult = objectMapper.writeValueAsString(allOrders);
        assertThat(actualResult).isEqualToIgnoringWhitespace(expectedResult);
    }

    @Test
    public void testCreateOrder() throws Exception {
        FixerUser user = new FixerUser("stbe", "9999", UserRole.CLIENT);
        ClientOrder clientOrder = new ClientOrder();
        ClientOrder savedOrder = new ClientOrder();
        clientOrder.setClient(user);
        savedOrder.setClient(user);
        Mockito.when(orderRepository.save(clientOrder)).thenReturn(savedOrder);
        String createOrderEndPoint = "/fixer/api/order/create";
        String toSaveJson = objectMapper.writeValueAsString(clientOrder);
        mockMvc.perform(post(createOrderEndPoint).content(toSaveJson));
    }

    @Test
    public void testGetNotAssignedOrders() {

    }

    @Test
    public void testGetAssignedOrders() {

    }

    @Test
    public void testGetAssignedOrdersInProgress() {

    }

    @Test
    public void testAssignOrderToEnginer() {

    }


}
