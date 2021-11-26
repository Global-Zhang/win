package com.hereWeGo.manager.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("image")
public class ImagesController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    //返回图片，所以通过response返回，不通过跳转页面或对象返回
    @RequestMapping("getKaptchaImage")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response){

        // 定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        //---------------生成验证码----------------//
        String text = defaultKaptcha.createText();
        System.out.println("验证码内容："+text);
        //将验证码放入session中
        request.getSession().setAttribute("pictureVerifyKey",text);
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try{
            outputStream = response.getOutputStream();
            ImageIO.write(image,"jpg",outputStream);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
           try{
               if (null != outputStream){
                   outputStream.close();
               }
           }catch (IOException e){
               e.printStackTrace();
           }
        }
        //---------------生成验证码----------------//
    }
}
