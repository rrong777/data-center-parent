package com;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
// 说明这整个类是一个测试用例
@SpringBootTest
public class FileTest {
    @Autowired
    private WebApplicationContext wac;
    // 测试的是web环境，要伪造一个MVC环境 伪造的环境并不会真正的去启动tomcat  非常快
    private MockMvc mockMvc;

    // 写了bofore注解的方法会在每一个测试用例运行前运行
    @Before
    public void setUp() {
        // 构建mockMvc， 讲wac 容器放进去构建
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    @Test
    public void whenUploadSuccess() throws Exception {
        String result = mockMvc.perform(fileUpload("/file")
                .file(new MockMultipartFile("file",
                        "text.txt",
                        "multipart/form-data",
                        "hello upload".getBytes("UTF-8"))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }
}
