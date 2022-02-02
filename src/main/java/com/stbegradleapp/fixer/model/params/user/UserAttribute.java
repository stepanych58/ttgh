package com.stbegradleapp.fixer.model.params.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.stbegradleapp.fixer.model.params.AttrType;
import com.stbegradleapp.fixer.model.params.order.ListValue;
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

@Entity
@Getter
@Setter
@ToString(of={"id", "name", "type"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAttribute {
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
    private List<UserParameter> parameters;
    //    @JsonIgnore
    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListValue> listValues;


    public UserAttribute(String name, AttrType type) {
        this.name = name;
        this.type = type;
    }

    public UserAttribute() {

    }
}
