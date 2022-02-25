package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// restController와 일반 controller의 차이
// 일반 controller는 뷰 템플릿을 반환 / restcontroller는 JSON이나 텍스트 즉 데이터를 반환한다.

@RestController  // RestAPI용 컨트롤러!  JSON을 반환해준다.
public class FristApiController {

    @GetMapping("/api/hello")
    public String hello(){
        return "hello world!";
    }
}
