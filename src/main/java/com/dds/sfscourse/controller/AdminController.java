package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.entity.Admin;
import com.dds.sfscourse.repo.AdminRepo;
import com.dds.sfscourse.security.JwtAuthenticationToken;
import com.dds.sfscourse.util.PasswordEncoder;
import com.dds.sfscourse.util.SecurityUtils;
import com.dds.sfscourse.vo.LoginBean;
import com.dds.sfscourse.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationManager;

@RestController
@RequestMapping("/")
public class AdminController {
    @Autowired
    AdminRepo adminRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/login")
    ResultBean login(HttpSession session, @RequestBody LoginBean loginBean, HttpServletRequest request){
        Integer id = loginBean.getId();
        String passwd = loginBean.getPasswd();

        Admin admin = adminRepo.findOne(id);

        System.out.println(loginBean);
        System.out.println(admin);

        if(admin==null)
            throw new BaseException(ResultEnum.LOGIN_ADMIN_NOT_FOUND);

        if (admin.getState() == 1) {
            throw new BaseException(ResultEnum.LOGIN_ACCOUNT_FREEZED);
        }

        //session.setAttribute();
        // 系统登录认证

        System.out.println(String.format("rawPass=%s encodePass=%s",passwd,new PasswordEncoder(admin.getSalt()).encode(passwd) ));

        JwtAuthenticationToken token = SecurityUtils.login(request,id.toString() , passwd, authenticationManager);

        return ResultHandler.ok(new TokenVo(token));
        //return ResultHandler.ok("s");
        //    throw new BaseException(ResultEnum.LOGIN_WRONG_PASSWD);
    }


}
