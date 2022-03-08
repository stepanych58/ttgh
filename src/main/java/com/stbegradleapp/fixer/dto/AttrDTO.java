package com.stbegradleapp.fixer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@Data
public class AttrDTO implements Serializable {
    private String attrId, name, type;
    private List<String> listValues;
}
