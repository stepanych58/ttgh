package com.stbegradleapp.fixer.repositories;

import com.stbegradleapp.fixer.model.params.user.UserParameter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UserParameterRepository extends CrudRepository<UserParameter, BigInteger> {
}
