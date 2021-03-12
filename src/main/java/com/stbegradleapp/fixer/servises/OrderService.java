package com.stbegradleapp.fixer.servises;

import com.stbegradleapp.fixer.exceptions.FixerUserException;
import com.stbegradleapp.fixer.exceptions.OrderException;
import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.repositories.ClientOrderRepository;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class OrderService {

    @Autowired
    ClientOrderRepository clientOrderRepository;
    @Autowired
    FixerUserRepository userRepository;

    public Iterable<ClientOrder> all() {
        return clientOrderRepository.findAll();
    }

    public ClientOrder assignEngeenerToOrder(BigInteger orderId, BigInteger engeenerId) {
        ClientOrder clientOrder = clientOrderRepository.findById(orderId).orElseThrow(() ->
                new OrderException(OrderException.ORDER_NOT_FOUND, orderId));
        FixerUser engineer = userRepository.findById(engeenerId).orElseThrow(() ->
                new FixerUserException(FixerUserException.USER_NOT_FOUND, engeenerId));
        clientOrder.setEngineer(engineer);
        return clientOrder;
    }

}
