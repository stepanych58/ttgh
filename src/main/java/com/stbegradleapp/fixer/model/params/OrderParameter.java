package com.stbegradleapp.fixer.model.params;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stbegradleapp.fixer.model.ClientOrder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Setter
@Getter
@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

//    @JsonIgnore
    @ManyToOne
    private OrderAttribute attribute;

    @Column(length = 1000)
    private String value;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private ClientOrder order;

    public OrderParameter(OrderAttribute attribute, String value, ClientOrder order) {
        this.attribute = attribute;
        this.value = value;
        this.order = order;
    }

    public OrderParameter() {

    }

    public OrderParameter(OrderAttribute attribute, String value) {
        this.attribute = attribute;
        this.value = value;

    }

    public void setOrder(ClientOrder clientOrder) {
        setOrder(clientOrder, true);
    }

    public void setOrder(ClientOrder clientOrder, boolean add) {
        this.order = clientOrder;
        if (clientOrder != null && add) {
            clientOrder.addParameter(this, false);
        }
    }


    @Override
    public String toString() {
        return "OrderParameter{" +
                "id=" + id +
                ", attribute={" + attribute.getName() + ", " +attribute.getId() + "}" +
                ", value='" + value + '\'' +
                ", order=" + (order == null ? "null": order.getId()) +
                '}';
    }
}
