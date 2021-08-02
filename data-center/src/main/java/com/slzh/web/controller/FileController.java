package com.slzh.web.controller;

import com.slzh.model.http.HttpResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${file.uploadPath}")
    private String uploadPath;

    /**
     * 上传文件 保存本地
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping
    public HttpResult upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = uploadPath + File.separator + file.getOriginalFilename();
        File localFile = new File(filePath);
        // 把上传上来的这个文件转换成本地文件
        file.transferTo(localFile);
        return HttpResult.ok();
    }


}
