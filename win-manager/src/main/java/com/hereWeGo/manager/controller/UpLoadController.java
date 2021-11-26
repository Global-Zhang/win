package com.hereWeGo.manager.controller;

import com.hereWeGo.common.result.FileResult;
import com.hereWeGo.manager.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/fileUpload")
public class UpLoadController {
    @Autowired
    private UploadService uploadService;

    @RequestMapping("save")
    @ResponseBody
    public FileResult upload(MultipartFile file) throws IOException{
        String filename = file.getOriginalFilename();
        String date = DateTimeFormatter.ofPattern("yyyy/MM/dd/").format(LocalDateTime.now());
        filename = date + System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));
        return uploadService.upload(file.getInputStream(),filename);
    }
}
