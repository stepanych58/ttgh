package com.stbegradleapp.fixer.dto;

import com.stbegradleapp.fixer.model.params.AttrType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class OrderParameterDTO {
    String name;
    BigInteger attrId;
    AttrType type;
    String value;
}
