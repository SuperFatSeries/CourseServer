package com.dds.sfscourse.controller;

import com.dds.sfscourse.repo.StudentCourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentCourseController {
    @Autowired
    StudentCourseRepo studentCourseRepo;
}
