package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

// CrudRepository를 상속받아 CrudRepository가 가지고 있는 기능들을 사용할 수 있도록 해준다.
// CrudRepository<관리 대상 entity, 관리 대상 entity의 대표값의 타입> 으로 상속해준다.
//  >> Article(관리 대상 entity)을 CURD하는 추가코드 구현없이 사용할 수 있게 된다.
public interface ArticleRepository extends CrudRepository<Article, Long> {
}
