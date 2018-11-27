package com.dds.sfscourse.controller;

import com.dds.sfscourse.repo.InstituteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstituteController {
    @Autowired
    InstituteRepo instituteRepo;
}
