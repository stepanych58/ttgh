package com.stbegradleapp.fixer.servises;

import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.UserRole;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    FixerUserRepository userRepository;

    @Transactional
    public FixerUser createUser(){
        FixerUser m1 = new FixerUser("Stepan2","89456549865", UserRole.ADMIN);
        return userRepository.save(m1);
    }
}
