package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.dto.StudentCommitDto;
import com.dds.sfscourse.entity.Homework;
import com.dds.sfscourse.entity.HomeworkSubmit;
import com.dds.sfscourse.repo.HomeworkSubmitRepo;
import com.dds.sfscourse.service.MongoDBService;
import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class HomeworkSubmitController {
    @Autowired
    HomeworkSubmitRepo homeworkSubmitRepo;

    @Autowired
    MongoDBService mongoDBService;

    @GetMapping(value = "/course/{courseId}/homework/{homeworkId}/submit")
    ResultBean getHomeworkSubmits(HttpSession session, @PathVariable int courseId, @PathVariable int homeworkId){
        int page=0,pageSize=10;

        PageRequest request = new PageRequest(page,pageSize);
        Homework homework = new Homework();
        homework.setId(homeworkId);
        Page<HomeworkSubmit> homeworkSubmitPage = homeworkSubmitRepo.findHomeworkSubmitByHomework(homework,request);

        List<HomeworkSubmit> homeworkSubmitList = homeworkSubmitPage.getContent();

        List<StudentCommitDto> studentCommitDtoList = new ArrayList<StudentCommitDto>();

        for (HomeworkSubmit submit:homeworkSubmitList){
            studentCommitDtoList.add(new StudentCommitDto(submit,homeworkSubmitRepo.getCommittedCountByHomeworkIdAndStudentId(submit.getHomework().getId(),submit.getStudent().getId())));
        }

        return ResultHandler.ok(studentCommitDtoList);
    }

    @PutMapping(value = "/course/{courseId}/homework/{homeworkId}/submit")
    ResultBean putHomeworkSubmit(HttpSession session, @PathVariable int courseId, @PathVariable int homeworkId, @RequestParam(value = "file") MultipartFile file){
        System.out.println("putHomework");
        System.out.println(file);

        GridFSInputFile homeworkSubmitFile = mongoDBService.save(file);

        if (homeworkSubmitFile == null) {
            throw new BaseException(ResultEnum.FILE_UPLOAD_FAIL);
        }


        // TODO: 2018/12/9

        Homework homework = new Homework();
        homework.setId(homeworkId);

        return ResultHandler.ok(homework);
    }

    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/homework/{homeworkId}/submit/{submit_id}")
    ResultBean deleteHomeworkSubmit(HttpSession session, @PathVariable int courseId, @PathVariable int homeworkId, @PathVariable int submitId) {
        homeworkSubmitRepo.delete(submitId);
        HomeworkSubmit homeworkSubmit = homeworkSubmitRepo.findOne(submitId);
        if (homeworkSubmit != null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(homeworkSubmit);
    }

    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @PostMapping(value = "/course/{courseId}/homework/{homeworkId}/submit/{submit_id}")
    ResultBean updateHomeworkSubmit(@RequestBody HomeworkSubmit homeworkSubmit) {

        homeworkSubmit.setUpdateTime(new Date().getTime());
        HomeworkSubmit homeworkSubmitResult = homeworkSubmitRepo.save(homeworkSubmit);
        if(homeworkSubmitResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(homeworkSubmitResult);
    }
}
