package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    //GetMapping을 통해서 접속할 URL주소를 넣어주어야 반환값으로 접속이 가능하다
    @GetMapping("/hi")
    public String niceToMeetYou(Model model){
        model.addAttribute("username", "KilHwan");
        return "greetings";  // templates/greetings.mustache -> 브라우저로 전송!
    }

    @GetMapping("/bye")
    public String SeeYouNext(Model model){
        model.addAttribute("nickname", "홍길동");
        return "goodbye";
    }
}
