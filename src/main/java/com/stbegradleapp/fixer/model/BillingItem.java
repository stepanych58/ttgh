package com.stbegradleapp.fixer.model;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
public class BillingItem {
    @Id
    @GeneratedValue
    private BigInteger id;
    @Column(nullable = false)
    private Integer value;
    @ManyToOne
    private ClientOrder order;
//    private Currency currency;
}
