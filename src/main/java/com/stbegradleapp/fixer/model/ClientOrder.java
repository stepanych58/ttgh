package com.stbegradleapp.fixer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stbegradleapp.fixer.model.params.OrderParameter;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
@Getter
@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column
    private OrderStatus status;


    public void setParameters(List<OrderParameter> parameters) {
        this.parameters = parameters;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order",
            fetch = FetchType.EAGER)
    private List<OrderParameter> parameters;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY)
//    private List<BillingItem> billingItems;
//    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER, optional = false)
    private FixerUser client;

    @ManyToOne(fetch = FetchType.LAZY)
    private FixerUser engineer;

    public ClientOrder(List<OrderParameter> parameters, FixerUser client, FixerUser engineer, OrderStatus status) {
        this.parameters = parameters;
        //set for all parameters order_id
        for(OrderParameter parameter: this.parameters) {
            parameter.setOrder(this);
        }
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

    public void addParameter(OrderParameter orderParameter) {
        addParameter(orderParameter, true);
    }

    public void addParameter(OrderParameter orderParameter, boolean set) {
        if (orderParameter != null) {
            if(!getParameters().contains(orderParameter)) {
                getParameters().add(orderParameter);
            }
            if (set) {
                orderParameter.setOrder(this, false);
            }
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
}
