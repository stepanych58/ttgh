package com.stbegradleapp.fixer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserParameterDTO {
    private String name, type, value;
}
