package com.dds.sfscourse.controller;

import com.dds.sfscourse.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    @Autowired
    StudentRepo studentRepo;
}
