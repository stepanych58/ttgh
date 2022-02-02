package com.stbegradleapp.fixer.servises.user;

import com.stbegradleapp.fixer.model.FixerUser;
import com.stbegradleapp.fixer.repositories.FixerUserRepository;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    FixerUserRepository userRepository;
    MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        final FixerUser fixerUser = findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new UsernameNotFoundException(
                        MessageFormatter.format(messages.getMessage("JdbcDaoImpl.notFound"), phoneNumber).getMessage()));
        return UserDetailsImpl.build(fixerUser);
    }

    public Optional<FixerUser> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

}
