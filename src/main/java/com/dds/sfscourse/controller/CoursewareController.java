package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.entity.Course;
import com.dds.sfscourse.entity.Courseware;
import com.dds.sfscourse.repo.CoursewareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class CoursewareController {
    @Autowired
    private CoursewareRepo coursewareRepo;

    @GetMapping(value = "/course/{courseId}/ware")
    ResultBean getCoursewares(HttpSession session, @PathVariable int courseId){
        int page=0,pageSize=10;

        PageRequest request = new PageRequest(page,pageSize);
        Page<Courseware> coursewarePage = coursewareRepo.findCoursewareByCourse(new Course(courseId),request);

        List<Courseware> coursewareList = coursewarePage.getContent();

        return ResultHandler.ok(coursewareList);
    }

    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @PutMapping(value = "/course/{courseId}/ware/")
    ResultBean putCourseware(HttpSession session, @PathVariable int courseId, @RequestBody Courseware courseware){

        courseware.setUpdateTime(new Date().getTime());
        coursewareRepo.save(courseware);

        return ResultHandler.ok(courseware);
    }

    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/ware/")
    ResultBean deleteCoursewares(HttpSession session, @PathVariable int courseId, @RequestParam(value = "courseware_delete_list") List<Integer> coursewareIdDeleteList){
        if(coursewareIdDeleteList==null || coursewareIdDeleteList.size()==0) {
            int affectedRows = coursewareRepo.deleteCoursewaresByCourse_Id(new Course(courseId));
            return ResultHandler.ok(affectedRows);
        }else{
            List<Courseware> coursewareDeleteList = null;
            for(Integer i:coursewareIdDeleteList){
                coursewareDeleteList.add(new Courseware(i));
            }
            coursewareRepo.delete(coursewareDeleteList);
            List<Courseware> coursewareList= coursewareRepo.findAll(coursewareIdDeleteList);
            return ResultHandler.ok(coursewareList);
        }
    }

    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/ware/{coursewareId}")
    ResultBean deleteCourseware(HttpSession session, @PathVariable int courseId, @PathVariable int coursewareId){
        coursewareRepo.delete(coursewareId);

        Courseware courseware = coursewareRepo.findOne(coursewareId);

        if (courseware != null)
            throw new BaseException(ResultEnum.FILE_DELETE_FAIL);

        return ResultHandler.ok(courseware);
    }

    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @PostMapping(value = "/course/{courseId}/ware/{coursewareId}")
    ResultBean updateCourseware(HttpSession session, @PathVariable int courseId, @PathVariable int coursewareId, @RequestBody Courseware courseware){
        courseware.setUpdateTime(new Date().getTime());
        Courseware coursewareUpdated= coursewareRepo.save(courseware);

        return ResultHandler.ok(coursewareUpdated);
    }

    @GetMapping(value = "/course/{courseId}/ware/{coursewareId}/download")
    ResponseEntity<InputStreamResource> downloadCourseware(HttpSession session, @PathVariable int courseId, @PathVariable int coursewareId) throws IOException {
        File file = new File("C:\\git\\test.txt");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment;fileName=" + file.getName());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
}
