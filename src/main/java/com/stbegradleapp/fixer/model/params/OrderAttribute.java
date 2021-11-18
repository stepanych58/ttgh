package com.stbegradleapp.fixer.model.params;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

/**
 * Order Attributes
 */
@Entity
@Getter
@Setter
@ToString(of={"id", "name", "type"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private BigInteger id;
    @Column(nullable = false)
    private String name;
    @Column(/*nullable = false*/)
    private AttrType type;
    @JsonIgnore
    @OneToMany(mappedBy = "attribute", orphanRemoval = true)
    private List<OrderParameter> parameters;
//    @JsonIgnore
    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListValue> listValues;


    public OrderAttribute(String name, AttrType type) {
        this.name = name;
        this.type = type;
    }
    public OrderAttribute(String name, AttrType type, List<ListValue> listValues) {
        this.name = name;
        this.type = type;
        listValues.stream().forEach(lv -> lv.setAttribute(this));
        this.listValues = listValues;
    }

    public OrderAttribute() {

    }
}
