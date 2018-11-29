package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.dao.HomeworkSubmitDao;
import com.dds.sfscourse.model.HomeworkSubmit;
import com.dds.sfscourse.service.MongoDBService;
import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

//import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class HomeworkSubmitController {
    HomeworkSubmitDao homeworkSubmitDao;

    @Autowired
    MongoDBService mongoDBService;

    @GetMapping(value = "/course/{courseId}/homework/{homeworkId}/submit")
    ResultBean getHomeworkSubmits(HttpSession session, @PathVariable int courseId, @PathVariable int homeworkId){
        int page=0,pageSize=10;

        PageRequest request = new PageRequest(page,pageSize);
        //Homework homework = new Homework();
        //homework.setId(homeworkId);
        Page<HomeworkSubmit> homeworkSubmitPage = null;//homeworkSubmitDao.findHomeworkSubmitByHomework(homework,request);

        List<HomeworkSubmit> homeworkSubmitList = homeworkSubmitPage.getContent();

        return ResultHandler.ok(homeworkSubmitList);
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

        //Homework homework = new Homework();
        //homework.setId(homeworkId);

        return ResultHandler.ok(null);
    }

    //@PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/homework/{homeworkId}/submit/{submit_id}")
    ResultBean deleteHomeworkSubmit(HttpSession session, @PathVariable int courseId, @PathVariable int homeworkId, @PathVariable int submitId) {
        //homeworkSubmitDao.delete(submitId);
        HomeworkSubmit homeworkSubmit = null;//homeworkSubmitDao.findOne(submitId);
        if (homeworkSubmit != null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(homeworkSubmit);
    }

    //@PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @PostMapping(value = "/course/{courseId}/homework/{homeworkId}/submit/{submit_id}")
    ResultBean updateHomeworkSubmit(@RequestBody HomeworkSubmit homeworkSubmit) {

        //homeworkSubmit.setUpdateTime(new Date().getTime());
        HomeworkSubmit homeworkSubmitResult = null;// homeworkSubmitDao.save(homeworkSubmit);
        if(homeworkSubmitResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(homeworkSubmitResult);
    }
}
