package com.stbegradleapp.fixer.servises.user;

import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.model.UserRole;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import lombok.SneakyThrows;
import org.glassfish.jersey.internal.guava.Lists;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    FixerUserRepository userRepository;

    MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public Optional<FixerUser> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public FixerUser save(FixerUser user) {
        return userRepository.save(user);
    }

    public List<FixerUser> getAllEngeeners() {
        return Lists.newArrayList(userRepository.findAllByRole(UserRole.ENGINEER));
    }

    public Iterable<FixerUser> findByRole(UserRole role) {
        return userRepository.findAllByRole(role);
    }

    public List<FixerUser> getAllUsers() {
        return Lists.newArrayList(userRepository.findAll());
    }

    public FixerUser getUserById(String userId) {
        BigInteger bigInteger = new BigInteger(userId);
        FixerUser user = userRepository.findById(bigInteger).orElseThrow(
                () ->
                        new UsernameNotFoundException(
                                MessageFormatter.format(messages.getMessage("JdbcDaoImpl.notFound"), userId).getMessage())
        );
        return user;
    }
}
