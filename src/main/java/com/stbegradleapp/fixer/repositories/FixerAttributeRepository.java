package com.stbegradleapp.fixer.repositories;

import com.stbegradleapp.fixer.model.params.OrderAttribute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface FixerAttributeRepository extends CrudRepository<OrderAttribute, BigInteger> {
    @Override
    void deleteById(BigInteger bigInteger);
}
