package com.jing.jinginterface.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
public class ImageController {
   private final String IMAGE_URL = "https://api.btstu.cn/sjbz/api.php";

    @GetMapping("/item")
    public String getImage(){
        HttpResponse httpResponse = HttpRequest.get(IMAGE_URL)
             .execute();
        String body = httpResponse.header("Location");
        return body;
    }
}
