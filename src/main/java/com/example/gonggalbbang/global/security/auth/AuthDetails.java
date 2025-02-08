package com.example.gonggalbbang.global.security.auth;

import com.example.gonggalbbang.domain.auth.presentation.dto.UserVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class AuthDetails implements UserDetails {

    private final UserVO userVO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new  SimpleGrantedAuthority(userVO.role().toString()));
    }

    @Override
    public String getPassword() {
        return this.userVO.password();
    }

    @Override
    public String getUsername() {
        return this.userVO.username();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}