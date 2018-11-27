package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.Exception.ResourceNotFoundException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.dto.HomeworkDto;
import com.dds.sfscourse.entity.Course;
import com.dds.sfscourse.entity.Homework;
import com.dds.sfscourse.repo.HomeworkRepo;
import com.dds.sfscourse.repo.HomeworkSubmitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class HomeworkController {
    @Autowired
    private HomeworkRepo homeworkRepo;

    @Autowired
    private HomeworkSubmitRepo homeworkSubmitRepo;

    @GetMapping(value = "/course/{courseId}/homework")
    ResultBean getHomeworks(HttpSession session, @PathVariable int courseId){
        int page=0,pageSize=10;

        PageRequest request = new PageRequest(page,pageSize);
        Page<Homework> homeworkPage = homeworkRepo.findHomeworkByCourse(new Course(courseId),request);

        List<Homework> homeworkList = homeworkPage.getContent();

        List<HomeworkDto> homeworkDtoList = new ArrayList<HomeworkDto>();

        for (Homework h:homeworkList){
            homeworkDtoList.add(new HomeworkDto(h,homeworkSubmitRepo.getCommittedCountByHomeworkId(h.getId())));
        }

        return ResultHandler.ok(homeworkDtoList);
    }

    @GetMapping(value = "/course/{courseId}/homework/{homeworkId}")
    ResultBean getHomework(HttpSession session, @PathVariable int courseId, @PathVariable int homeworkId) {
        Homework homework = homeworkRepo.findOne(homeworkId);
        if (homework == null)
            throw new ResourceNotFoundException();
        return ResultHandler.ok(new HomeworkDto(homework));
    }

    @PutMapping(value = "/course/{courseId}/homework")
    ResultBean addHomework(@RequestBody Homework homework){
        Homework homeworkResult = homeworkRepo.save(homework);
        if(homeworkResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(homeworkResult);
    }

    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/homework/{homeworkId}")
    ResultBean deleteHomework(HttpSession session, @PathVariable int courseId, @PathVariable int homeworkId) {
        homeworkRepo.delete(courseId);
        Homework homework = homeworkRepo.findOne(homeworkId);
        if (homework != null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(homework);
    }

    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @PostMapping(value = "/course/{courseId}/homework/{homeworkId}")
    ResultBean updateHomework(@RequestBody Homework homework) {

        homework.setUpdateTime(new Date().getTime());
        Homework homeworkResult = homeworkRepo.save(homework);
        if(homeworkResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(homeworkResult);
    }
}
