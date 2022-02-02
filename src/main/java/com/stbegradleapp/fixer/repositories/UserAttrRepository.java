package com.stbegradleapp.fixer.repositories;

import com.stbegradleapp.fixer.model.params.user.UserAttribute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UserAttrRepository extends CrudRepository<UserAttribute, BigInteger> {
    UserAttribute findByName(String Name);
}
