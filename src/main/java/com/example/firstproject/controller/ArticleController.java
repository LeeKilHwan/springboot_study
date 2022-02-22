package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @Autowired  // >> spring boot가 미리 생성해놓은 객체를 가져다가 자동 연결해준다. ( A a = new A() 를 할필요가 없다)
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        System.out.println(form.toString());

        // 1. Dto를 변환 ( Entity로 변환해주어야 한다.)
        Article article = form.toEntity();
        System.out.println(article.toString());

        // 2. Repository에게 Entity를 DB안에 저장하게 해준다.
        Article saved = articleRepository.save(article);
        System.out.println(saved.toString());
        

        return "";
    }
}
