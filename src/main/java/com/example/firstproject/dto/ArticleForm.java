package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor  // 생성자를 기본으로 만들어주는 롬복 어노테이션
@ToString            // 자동으로 toString 메소드를 만들어주는 롬복 어노테이션
public class ArticleForm {

    private String title;
    private String content;


    public Article toEntity() {
        return new Article(null, title, content);
    }
}
