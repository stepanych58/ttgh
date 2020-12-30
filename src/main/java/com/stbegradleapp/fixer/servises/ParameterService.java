package com.stbegradleapp.fixer.servises;

import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.params.OrderParameter;
import com.stbegradleapp.fixer.repositories.OrderParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterService {
    @Autowired
    OrderParameterRepository parameterRepository;

    public void initParametersOrder(ClientOrder clientOrder, List<OrderParameter> parameters) {
        for (OrderParameter param : parameters) {
            param.setOrder(clientOrder);
        }
        parameterRepository.saveAll(parameters);
    }

}
