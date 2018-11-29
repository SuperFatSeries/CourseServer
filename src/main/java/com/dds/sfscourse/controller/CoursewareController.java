package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.dao.CoursewareDao;
import com.dds.sfscourse.model.Courseware;
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
import java.util.List;

//import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class CoursewareController {
    private CoursewareDao coursewareDao;

    @GetMapping(value = "/course/{courseId}/ware")
    ResultBean getCoursewares(HttpSession session, @PathVariable int courseId){
        int page=0,pageSize=10;

        PageRequest request = new PageRequest(page,pageSize);
        Page<Courseware> coursewarePage = coursewareDao.findCoursewareByCourse(new Integer(courseId),request);

        List<Courseware> coursewareList = coursewarePage.getContent();

        return ResultHandler.ok(coursewareList);
    }

    //@PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @PutMapping(value = "/course/{courseId}/ware/")
    ResultBean putCourseware(HttpSession session, @PathVariable int courseId, @RequestBody Courseware courseware){

        //courseware.setUpdateTime(new Date().getTime());
        //coursewareDao.save(courseware);

        return ResultHandler.ok(null);
    }

    //@PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/ware/")
    ResultBean deleteCoursewares(HttpSession session, @PathVariable int courseId, @RequestParam(value = "courseware_delete_list") List<Long> coursewareIdDeleteList){
        /*if(coursewareIdDeleteList==null || coursewareIdDeleteList.size()==0) {
            int affectedRows = coursewareDao.deleteCoursewaresByCourse_Id(new Integer(courseId));
            return ResultHandler.ok(affectedRows);
        }else{
            List<Courseware> coursewareDeleteList = null;
            for(Long i:coursewareIdDeleteList){
                coursewareDeleteList.add(new Courseware(i));
            }
            coursewareDao.delete(coursewareDeleteList);
            List<Courseware> coursewareList= coursewareDao.findAll(coursewareIdDeleteList);
            return ResultHandler.ok(null);
        }*/
        return ResultHandler.ok(null);
    }

    //@PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/ware/{coursewareId}")
    ResultBean deleteCourseware(HttpSession session, @PathVariable int courseId, @PathVariable int coursewareId){
        //coursewareDao.delete(coursewareId);

        Courseware courseware = null;//coursewareDao.findOne(coursewareId);

        if (courseware != null)
            throw new BaseException(ResultEnum.FILE_DELETE_FAIL);

        return ResultHandler.ok(courseware);
    }

    //@PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @PostMapping(value = "/course/{courseId}/ware/{coursewareId}")
    ResultBean updateCourseware(HttpSession session, @PathVariable int courseId, @PathVariable int coursewareId, @RequestBody Courseware courseware){
        //courseware.setUpdateTime(new Date().getTime());
        Courseware coursewareUpdated= null;//coursewareDao.save(courseware);

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
