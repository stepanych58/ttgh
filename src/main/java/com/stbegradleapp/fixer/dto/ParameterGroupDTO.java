package com.stbegradleapp.fixer.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class ParameterGroupDTO {
    private String groupName;
    private int order;
    private List<ParameterDTO> parameters;
}
