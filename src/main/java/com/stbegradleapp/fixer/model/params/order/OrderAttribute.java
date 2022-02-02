package com.stbegradleapp.fixer.model.params.order;

import com.stbegradleapp.fixer.model.params.AttrType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Order Attributes
 */
@Entity
@Getter
@Setter
@ToString(of={"id", "name", "type"})
public class OrderAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private BigInteger id;
    @Column(nullable = false)
    private String name;
    @Column(/*nullable = false*/)
    private AttrType type;

    @OneToMany(mappedBy = "attribute", orphanRemoval = true)
    private List<OrderParameter> parameters;

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
