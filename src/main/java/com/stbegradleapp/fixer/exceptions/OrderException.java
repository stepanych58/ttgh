package com.stbegradleapp.fixer.exceptions;

import org.hibernate.criterion.Order;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.function.Supplier;

public class OrderException extends RuntimeException {
    public static final String ORDER_NOT_FOUND = "Order {0} not found";
    public OrderException(String message, BigInteger orderId) {
         super(MessageFormat.format(message, orderId));
    }
}
