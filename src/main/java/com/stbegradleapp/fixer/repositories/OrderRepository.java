package com.stbegradleapp.fixer.repositories;

import com.stbegradleapp.fixer.model.ClientOrder;
import com.stbegradleapp.fixer.model.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<ClientOrder, BigInteger> {
    Iterable<ClientOrder> findAllByStatus(OrderStatus status);
}
