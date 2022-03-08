package com.stbegradleapp.fixer.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonInclude(value = NON_NULL)
@Data
public class UserDTO {
    private String firstName;
    private String secondName;
    private String phoneNumber;
    private String password;
    private List<ParameterDTO> parameters;
}
