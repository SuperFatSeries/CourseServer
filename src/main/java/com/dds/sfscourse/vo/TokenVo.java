package com.dds.sfscourse.vo;

import com.dds.sfscourse.security.JwtAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TokenVo {
    private String token;
    private List<String> authorities;

    public TokenVo(JwtAuthenticationToken jwtAuthenticationToken) {
        this.token = jwtAuthenticationToken.getToken();
        this.authorities = new ArrayList<String>();
        for (GrantedAuthority  grantedAuthority: jwtAuthenticationToken.getAuthoritiesString()){
            authorities.add(grantedAuthority.getAuthority());
        }
    }

    public TokenVo() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "TokenVo{" +
                "token='" + token + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
