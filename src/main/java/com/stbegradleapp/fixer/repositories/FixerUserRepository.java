package com.stbegradleapp.fixer.repositories;

import com.stbegradleapp.fixer.model.FixerUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface FixerUserRepository extends CrudRepository<FixerUser, BigInteger> {
}
