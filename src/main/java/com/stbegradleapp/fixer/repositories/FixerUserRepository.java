package com.stbegradleapp.fixer.repositories;

import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.params.user.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface FixerUserRepository extends CrudRepository<FixerUser, BigInteger> {
    Optional<FixerUser> findByPhoneNumber(String phoneNumber);
    Iterable<FixerUser> findAllByRole(UserRole userRole);
}
