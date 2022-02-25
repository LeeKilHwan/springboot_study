package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity  // >> DB가 해당 객체를 인식 가능하게 된다.

@AllArgsConstructor
@NoArgsConstructor  // 디폴트 생성자를 추가해주는 어노테이션
@ToString
@Getter
public class Article {
    @Id // 대표값을 지정! (주민등록번호 같은 것)  // 필드의 값이 같을 때 그것을 구별하기 위해 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 1, 2, 3, ... 자동 생성 어노테이션
                                                //(strategy = GenerationType.IDENTITY) >>  DB가 id를 자동 생성해준다.
    private Long id;

    @Column
    private String title;

    @Column
    private String content;


    // API로 수정하는 경우 입력한 값만 수정하게 해주는 메소드
    public void patch(Article article) {
        if (article.title != null){
            this.title = article.title;
        }
        if (article.content != null){
            this.content = article.content;
        }
    }


}
