package com.dds.sfscourse.base;


import com.dds.sfscourse.security.JwtUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<Integer> {

    @Override
    public Integer getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(String.format("authentication.getPrincipal().toString()=%s",authentication.getPrincipal().toString()));
        if(authentication.getPrincipal().toString().equals("anonymousUser"))
            return 0;
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) authentication.getPrincipal();
        String adminId = jwtUserDetails.getUsername();
        return Integer.parseInt(adminId);
    }
}
