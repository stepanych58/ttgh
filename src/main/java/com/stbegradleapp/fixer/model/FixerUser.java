package com.stbegradleapp.fixer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.List;

@Entity
@Getter
public class FixerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    protected BigInteger id;
    @Column(length = 200, nullable = false)
    protected String name;
    @Column(nullable = false, unique = true)
    protected String phoneNumber;
    //https://habr.com/ru/post/482552/
    @Column()
    @Size(min=2, message = "Не меньше 5 знаков")
    private String password;
    @Transient
    private String passwordConfirm;

    @Column
    protected UserRole role;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.LAZY)
    private List<ClientOrder> orders;

    public FixerUser(String name, String phoneNumber, UserRole role) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public FixerUser() {

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
