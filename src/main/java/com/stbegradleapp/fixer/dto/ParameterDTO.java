package com.stbegradleapp.fixer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stbegradleapp.fixer.model.params.AttrType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@RequiredArgsConstructor
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ParameterDTO {
    final String name;
    final BigInteger attrId;
    final AttrType type;
    final String value;
}
