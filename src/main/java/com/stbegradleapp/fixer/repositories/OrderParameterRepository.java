package com.stbegradleapp.fixer.repositories;

import com.stbegradleapp.fixer.model.params.OrderParameter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface OrderParameterRepository extends CrudRepository<OrderParameter, BigInteger> {
}
