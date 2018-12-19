package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.Exception.ResourceNotFoundException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.config.WebSecurityConfig;
import com.dds.sfscourse.entity.Admin;
import com.dds.sfscourse.entity.AdminRole;
import com.dds.sfscourse.repo.AdminRepo;
import com.dds.sfscourse.repo.AdminRoleRepo;
import com.dds.sfscourse.security.GrantedAuthorityImpl;
import com.dds.sfscourse.security.JwtAuthenticationToken;
import com.dds.sfscourse.security.JwtUserDetails;
import com.dds.sfscourse.util.PasswordEncoder;
import com.dds.sfscourse.util.SecurityUtils;
import com.dds.sfscourse.vo.AuthenVo;
import com.dds.sfscourse.vo.LoginBean;
import com.dds.sfscourse.vo.TokenVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/")
public class AdminController {
    @Autowired
    AdminRepo adminRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    AdminRoleRepo adminRoleRepo;

    @PostMapping(value = "/login")
    ResultBean login(HttpSession session, @RequestBody LoginBean loginBean, HttpServletRequest request){
        Integer id = loginBean.getId();
        String passwd = loginBean.getPasswd();

        Admin admin = adminRepo.findOne(id);

        System.out.println(loginBean);
        System.out.println(admin);

        if(admin==null)
            throw new BaseException(ResultEnum.LOGIN_ADMIN_NOT_FOUND);

        if (admin.getValid()==0) {
            throw new BaseException(ResultEnum.LOGIN_ACCOUNT_FREEZED);
        }

        //session.setAttribute();
        // 系统登录认证

        System.out.println(String.format("rawPass=%s encodePass=%s",passwd,new PasswordEncoder(admin.getSalt()).encode(passwd) ));

        JwtAuthenticationToken token = SecurityUtils.login(request,id.toString() , passwd, authenticationManager);

        return ResultHandler.ok(new TokenVo(token,admin.getName()));

        //return ResultHandler.ok("s");
        //    throw new BaseException(ResultEnum.LOGIN_WRONG_PASSWD);
    }

    @PutMapping(value = "/register")
    ResultBean login(HttpSession session, @RequestBody Admin admin) {
        assert admin.getId()==null;
        String passwd = admin.getPassword();
        String salt = admin.getName();
        String passwdEncoded = new PasswordEncoder(salt).encode(passwd);
        admin.setSalt(salt);
        admin.setPassword(passwdEncoded);
        Admin adminRes = adminRepo.save(admin);
        if(adminRes == null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(admin.getId());
    }

    @GetMapping(value = "/admin/info")
    ResultBean adminInfo(HttpSession session, HttpServletRequest request){
        System.out.println("Header:" + request.getHeader("Authorization"));
        System.out.println("JWT:" + SecurityContextHolder.getContext().getAuthentication());

        //Object jwtUserDetailsObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //if(jwtUserDetailsObj instanceof JwtUserDetails){
            JwtUserDetails jwtUserDetails =
                    (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());
            Admin admin = adminRepo.findOne(admin_id);

            //AdminRole adminRole = adminRoleRepo.findAdminRoleById(admin_id);

            Collection<GrantedAuthorityImpl> grantedAuthoritieList = (Collection<GrantedAuthorityImpl>)jwtUserDetails.getAuthorities();

            return ResultHandler.ok(new AuthenVo(admin.getName(),grantedAuthoritieList));
    }

    //admin列表
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping(value = "/admin")
    ResultBean getAdmins(HttpSession session,@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC)
            Pageable pageable) {
        System.out.println(pageable);
        Page<Admin> adminPage = adminRepo.findAdmins(pageable);
        return ResultHandler.ok(adminPage);
    }

    //admin信息
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping(value = "/admin/{adminId}")
    ResultBean getAdmin(HttpSession session, @PathVariable Integer adminId) {
        Admin admin = adminRepo.findAdminById(adminId);
        if (admin == null)
            throw new ResourceNotFoundException();
        return ResultHandler.ok(admin);
    }

}
