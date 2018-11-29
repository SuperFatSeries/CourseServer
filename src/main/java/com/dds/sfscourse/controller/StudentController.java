package com.dds.sfscourse.controller;

import com.dds.sfscourse.dao.StudentDao;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {
    StudentDao studentDao;
}
