package com.stbegradleapp.fixer.model;

import com.stbegradleapp.fixer.model.params.order.OrderParameter;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
@Getter
public class ClientOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column
    private OrderStatus status;


    @PrePersist
    public void onPrePersist() {
        setOrderForParams();
    }

    @PreUpdate
    public void onPreUpdate() {
        setOrderForParams();
    }

    public void setParameters(List<OrderParameter> parameters) {
        this.parameters = parameters;
        if (!CollectionUtils.isEmpty(parameters)){
            for (OrderParameter p : parameters) {
                p.setOrder(this);
            }
        }
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order",
            fetch = FetchType.EAGER)
    private List<OrderParameter> parameters;

    @ManyToOne(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER, optional = false)
    private FixerUser client;

    @ManyToOne(fetch = FetchType.LAZY)
    private FixerUser engineer;

    public ClientOrder(List<OrderParameter> parameters, FixerUser client, FixerUser engineer, OrderStatus status) {
        this.parameters = parameters;
        this.client = client;
        this.engineer = engineer;
        this.status = status;
    }


    public ClientOrder(FixerUser client, FixerUser engineer) {
        this.client = client;
        this.engineer = engineer;
    }

    public ClientOrder(FixerUser client, FixerUser engineer, OrderStatus status) {
        this.client = client;
        this.engineer = engineer;
        this.status = status;
    }

    public ClientOrder() {

    }

    public void setOrderForParams() {
        for(OrderParameter parameter: this.parameters) {
            parameter.setOrder(this);
        }
    }

    public void setClient(FixerUser client) {
        this.client = client;
    }

    public void setEngineer(FixerUser engineer) {
        this.engineer = engineer;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("");
        res.append("ClientOrder {")
                .append("id=").append(getId())
                .append("client =").append(getClient())
                .append("parameters =").append(getParameters())
                .append("}");
        return res.toString();
    }

    public OrderParameter getParameter(BigInteger attrId) {
        List<OrderParameter> parameters = getParameters();
        if (CollectionUtils.isEmpty(parameters)) {
            return null;
        }
        for (OrderParameter p : parameters) {
            if (p.getAttribute().getId().equals(attrId)) {
                return p;
            }
        }
        return null;
    }

    public void addParameter(OrderParameter parameter) {
        List<OrderParameter> parameters = getParameters();
        parameters.add(parameter);
        setParameters(parameters);
    }


}
