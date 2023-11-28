package com.codetest.jwtstudy.application.users;

import com.codetest.jwtstudy.domain.users.Users;
import com.codetest.jwtstudy.domain.users.UsersRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUserId(username)
            .map(this::createUserDetails)
            .orElseThrow(() -> new BadCredentialsException(username + " -> Not Found."));
    }

    private User createUserDetails(Users user) {
        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(user.getUserId(), user.getPassword(), grantedAuthorities);
    }

}
