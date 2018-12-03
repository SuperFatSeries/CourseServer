package com.dds.sfscourse;

import com.dds.sfscourse.service.MongoDBService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SfscourseApplicationTests {

    @Autowired
    MongoDBService mongoDBService;
    @Test
    public void contextLoads() {
        //mongoDBService.save(null);
    }

}
