package com.stbegradleapp.fixer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.stbegradleapp.fixer.model.params.user.UserParameter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.List;

@Entity
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FixerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    protected BigInteger id;
    @Column(length = 200, nullable = false)
    protected String name;
    @Column(nullable = false, unique = true)
    protected String phoneNumber;
    @Max(value = 5)
    @Column
    protected Double rate;


    //https://habr.com/ru/post/482552/

    @Column()
    @JsonIgnore
    @Size(min=2, message = "Не меньше 5 знаков")
    private String password;

    @Column
    private String photo;
//    @Transient
//    @JsonIgnore
//    private String passwordConfirm;

    @Column
    protected UserRole role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.LAZY)
    private List<ClientOrder> orders;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserParameter> parameters;

    public FixerUser(String name, String phoneNumber, UserRole role) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public FixerUser(String name, String pswd, String phoneNumber, UserRole role) {
        this.name = name;
        this.password = pswd;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public FixerUser() {

    }

    public UserParameter getParameterByName(String parameterName) {
        for (UserParameter parameter: getParameters()) {
            if (parameter.getName().equals(parameterName)) {
                return parameter;
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return "USER{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                '}';
    }
}
