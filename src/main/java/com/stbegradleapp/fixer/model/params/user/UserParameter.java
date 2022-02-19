package com.stbegradleapp.fixer.model.params.user;

import com.stbegradleapp.fixer.model.FixerUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Setter
@Getter
public class UserParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @ManyToOne
    private UserAttribute attribute;

    @Column(length = 1000)
    private String value;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private FixerUser user;

    /*
    *     @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private UserParameterGroup parameterGroup;*/

    public UserParameter(UserAttribute attribute, String value, FixerUser user) {
        this.attribute = attribute;
        this.value = value;
        this.user = user;
    }

    public UserParameter() {

    }

    public UserParameter(UserAttribute attribute, String value) {
        this.attribute = attribute;
        this.value = value;

    }

    public String getName() {
        return getAttribute().getName();
    }

    public String getType() {
        return getAttribute().getType().name();
    }

    @Override
    public String toString() {
        return "OrderParameter{" +
                "id=" + id +
                ", attribute={" + attribute.getName() + ", " +attribute.getId() + "}" +
                ", value='" + value + '\'' +
//                ", user=" + (user == null ? "null": user.getId()) +
                '}';
    }
}
