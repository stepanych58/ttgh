package com.stbegradleapp.fixer.model.params.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Getter
@Setter
public class ListValue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    @JsonIgnore
    @ManyToOne(optional = false)
    OrderAttribute attribute;
    @Column
    String value;

    public ListValue(String value) {
        this.value = value;
    }

    public ListValue() {

    }
}
