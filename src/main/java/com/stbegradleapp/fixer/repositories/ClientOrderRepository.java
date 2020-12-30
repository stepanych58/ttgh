package com.stbegradleapp.fixer.repositories;

import com.stbegradleapp.fixer.model.ClientOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ClientOrderRepository extends CrudRepository<ClientOrder, BigInteger> {
}
