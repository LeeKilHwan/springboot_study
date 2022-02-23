package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j  // 로깅을 위한 어노테이션
public class ArticleController {

    @Autowired  // >> spring boot가 미리 생성해놓은 객체를 가져다가 자동 연결해준다. ( A a = new A() 를 할필요가 없다)
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {

        // 프린트로 찍어보는것은 서버 기록에도 남지 않으며, 서버 성능에도 좋지 않은 영향을 준다.
        //System.out.println(form.toString());  -->> 로깅 기능으로 대체
        // 로깅 =>> 자동차의 블랙박스와 같은것 ( 서버에서 일어나는 일들을 확인하고 싶은 일들을 다 기록하는 것)
        // @Slf4j 해주면 로깅을 사용할 수 있다.
        log.info(form.toString());

        // 1. Dto를 변환 ( Entity로 변환해주어야 한다.)
        Article article = form.toEntity();
        // System.out.println(article.toString());
        log.info(article.toString());

        // 2. Repository에게 Entity를 DB안에 저장하게 해준다.
        Article saved = articleRepository.save(article);
        // System.out.println(saved.toString());
        log.info(saved.toString());


        return "";
    }
}
