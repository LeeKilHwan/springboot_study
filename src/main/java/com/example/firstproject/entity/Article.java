package com.example.firstproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity  // >> DB가 해당 객체를 인식 가능하게 된다.
public class Article {
    @Id // 대표값을 지정! (주민등록번호 같은 것)  // 필드의 값이 같을 때 그것을 구별하기 위해 사용
    @GeneratedValue  // 1, 2, 3, ... 자동 생성 어노테이션
    private Long id;

    @Column
    private String title;

    @Column
    private String content;


    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
