package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service  // 서비스 선언 (서비스 객체를 스프링 부트에 생성!)
public class ArticleService {
    @Autowired  // DI 해준다
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null)
            return null;
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. 수정용 entity를 생성
        Article article = dto.toEntity();
        log.info("id:{}, article:{}", id, article.toString());

        // 2. 대상 enttity 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if (target == null  || id != article.getId()){
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id:{}, article:{}", id, article.toString());
            return null;
        }
        // 4. 업데이트 및 정상 응답 (200)
        //(일부 정보만 수정할 경우에는 DB에서 조회한 target을 받아온 articleEntity로 붙여줘야 한다.) >> Article(엔티티 클래스)에 patch 메소드 정의해준다.
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (target == null){
            return null;
        }
        // 대상 삭제 후 응답 반환
        articleRepository.delete(target);
        return target;
    }
    @Transactional  //  >> 해당 메소드를 트랜잭션으로 묶는다. ( 해당 메소드가 실행되다가 실패하면 메소드가 실행되기 이전으로 록백한다.)
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 entity 묶음으로 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

//        // >> 위의 stream문법이 원래 for문으로 작성하면 이렇게 생겼다.
//        List<Article> articleList = new ArrayList<>();
//        for (int i = 0; i < dtos.size(); i++){
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articleList.add(entity);
//        }


        // entity 묶음을 DB로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

//        // >> 위의 stream문법이 원래 for문으로 작성하면 이렇게 생겼다.
//        for (int i = 0; i < articleList.size(); i++){
//            Article article = articleList.get(i);
//            articleRepository.save(article);
//        }


        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")
        );

        // 결과값 반환
        return articleList;
    }
}
