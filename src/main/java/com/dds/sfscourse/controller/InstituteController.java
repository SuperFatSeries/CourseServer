package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.Exception.ResourceExistException;
import com.dds.sfscourse.Exception.ResourceNotFoundException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.config.WebSecurityConfig;
import com.dds.sfscourse.entity.Institute;
import com.dds.sfscourse.entity.Student;
import com.dds.sfscourse.repo.InstituteRepo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class InstituteController {
    @Autowired
    InstituteRepo instituteRepo;

    //学院列表
    @GetMapping(value = "/institute")
    ResultBean getInstitutes(@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC)
                                   Pageable pageable){
        Page<Institute> studentPage = instituteRepo.findInstitutes(pageable);
        return ResultHandler.ok(studentPage);
    }

    //学院详细
    @GetMapping(value = "/institute/{instituteId}")
    ResultBean getInstitute(HttpSession session, @PathVariable int instituteId) {
        Institute institute = instituteRepo.findInstituteById(instituteId);
        if (institute == null)
            throw new ResourceNotFoundException();
        return ResultHandler.ok(institute);
    }

    //添加学院
    @PreAuthorize("hasAuthority('admin')")
    @PutMapping(value = "/institute")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean addInstitute(@RequestBody Institute institute){
        if(instituteRepo.findInstituteById(institute.getId())!=null)
            throw new ResourceExistException();

        // TODO: 2018/12/14 合法性判断

        Institute instituteResult = instituteRepo.save(institute);
        if(instituteResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(instituteResult);
    }

    //修改学院
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping(value = "/institute/{instituteId}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean postInstitute(@RequestBody Institute institute) {
        if(instituteRepo.findInstituteById(institute.getId())==null)
            throw new ResourceNotFoundException();

        // TODO: 2018/12/14 合法性判断

        Institute instituteResult=instituteRepo.save(institute);

        if (instituteResult == null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(instituteResult);
    }

    //删除学院
    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping(value = "/institute/{instituteId}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean deleteInstitute(HttpSession session, @PathVariable int instituteId) {
        int res = instituteRepo.deleteInstituteById(instituteId);
        if (res != 1)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(ResultEnum.SUCCESS);
    }
}
