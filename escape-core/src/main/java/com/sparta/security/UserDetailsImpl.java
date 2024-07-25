package com.sparta.security;

import com.sparta.domain.user.entity.User;
import com.sparta.domain.user.entity.UserType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final User user;

    public String getUserEmail() {
        return user.getEmail();
    }

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    } // email 수정

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserType role = user.getUserType();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getAuthority());

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(simpleGrantedAuthority);

        return authorities;
    }
}
