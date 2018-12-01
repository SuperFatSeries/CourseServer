package com.dds.sfscourse.security;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.dds.sfscourse.entity.Admin;
import com.dds.sfscourse.entity.AdminRole;
import com.dds.sfscourse.repo.AdminRepo;
import com.dds.sfscourse.repo.AdminRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户登录认证信息查询
 * @author Louis
 * @date Nov 20, 2018
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private AdminRoleRepo adminRoleRepo;

    @Autowired
    private StringRedisTemplate template;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepo.findAdminById(Integer.parseInt(username));
        if (admin == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }

        System.out.println(admin);

        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        List<AdminRole> adminRoleList = adminRoleRepo.findAdminRoleListByAdminId(admin.getId());
        Set<String> permissions = new HashSet<>();
        for (AdminRole adminRole:adminRoleList){
            permissions.add(adminRole.getRole().getName());
        }

        System.out.println(permissions);

        List<GrantedAuthority> grantedAuthorities = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());

        System.out.println(String.format("grantedAuthorities=%s",grantedAuthorities));

        return new JwtUserDetails(admin.getId().toString(), admin.getPassword(), admin.getSalt(), grantedAuthorities);
    }

}
