package com.stbegradleapp.fixer.model.params;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttrDTO implements Serializable {
    private String id, name, type;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<String> listValues;
}
