package com.jing.jinginterface.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentence")
public class SentenceController {

    private final String SENTENCE_URL = "https://api.btstu.cn/yan/api.php";

    @GetMapping("/sentence")
    public String getSentence(){
        HttpResponse httpResponse = HttpRequest.get(SENTENCE_URL)
                .execute();
        String body = httpResponse.body();
        return body;
    }
}
