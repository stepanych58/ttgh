package com.stbegradleapp.fixer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stbegradleapp.fixer.model.params.user.UserParameter;
import com.stbegradleapp.fixer.model.params.user.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.List;

import static com.stbegradleapp.fixer.model.params.user.UserRole.CLIENT;

@Entity
@Getter
@Setter
public class FixerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    protected BigInteger id;
    @Column(length = 200, nullable = false)
    protected String firstName;
    @Column(length = 200)
    protected String secondName;
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

    @Column
    protected UserRole role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<ClientOrder> orders;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "user")
    private List<UserParameter> parameters;

    public FixerUser(String name, String phoneNumber, UserRole role) {
        this.firstName = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public FixerUser(String name, String phoneNumber, String password) {
        this.firstName = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = CLIENT;
    }

    public FixerUser(String name, String pswd, String phoneNumber, UserRole role) {
        this(name, phoneNumber, role);
        this.password = pswd;
    }

    public FixerUser(String name, String secondName, String pswd, String phoneNumber, UserRole role) {
        this(name, pswd, phoneNumber, role);
        this.secondName = secondName;
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
                ", name='" + firstName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                '}';
    }
}
