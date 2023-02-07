package com.shoppingTest.common.security;

import com.shoppingTest.common.security.domain.CustomUser;
import com.shoppingTest.domain.Member;
import com.shoppingTest.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("userName : " + userName);

        Member member = repository.findByUserId(userName).get(0);

        log.info("member : " + member);

        return member == null ? null : new CustomUser(member);
    }
}
