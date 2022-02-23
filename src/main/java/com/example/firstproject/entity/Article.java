package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity  // >> DB가 해당 객체를 인식 가능하게 된다.

@AllArgsConstructor
@NoArgsConstructor  // 디폴트 생성자를 추가해주는 어노테이션
@ToString
public class Article {
    @Id // 대표값을 지정! (주민등록번호 같은 것)  // 필드의 값이 같을 때 그것을 구별하기 위해 사용
    @GeneratedValue  // 1, 2, 3, ... 자동 생성 어노테이션
    private Long id;

    @Column
    private String title;

    @Column
    private String content;



}
