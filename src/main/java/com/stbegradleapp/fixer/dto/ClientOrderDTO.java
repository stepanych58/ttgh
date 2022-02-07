package com.stbegradleapp.fixer.dto;

import com.stbegradleapp.fixer.model.OrderStatus;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;
@Data
public class ClientOrderDTO {
    BigInteger id;
    String client;
    String executor;
    OrderStatus status;
    List<OrderParameterDTO> parameters;
}
