package com.stbegradleapp.fixer.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.Map;

@Data
public class UpdateOrderDTO {
    BigInteger id;
    Map<BigInteger, String> parameters;
}
