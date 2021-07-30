package com;

import com.alibaba.fastjson.JSON;
import com.slzh.service.sso.util.SM2Util;
import com.slzh.service.sso.util.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@RestController
public class AppApplication {
    @GetMapping("/hello")
    public String user() {
        return "jojo";
    }

    public static void main(String[] args) throws IOException {



        SpringApplication.run(AppApplication.class, args);
    }
}
