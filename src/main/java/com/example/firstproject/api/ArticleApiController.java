package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController  // RestAPI용 컨틀롤러 데이터(JSON) 반환
public class ArticleApiController {

    @Autowired  // >> DI 해주어야 한다.  , 생성 객체를 가져와 연결!
    private ArticleService articleService;

    //GET

    // 1. 전체 목록 조회
    @GetMapping("/api/articles")
    public List<Article> index () {
        return articleService.index();
    }

    // 2. 단일 게시글 조회
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id ) {
        return articleService.show(id);
    }

    //POST
    @PostMapping("/api/articles")
    // 일반적인 controller에서는 dto에 파라미터만 추가해서 데이터를 보내면 됬었지만
    // restAPIcontroller에서는 JSON으로 데이터를 보낼 때에 RequestBdoy 어노테이션을 붙여주어야 한다.
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        log.info(dto.toString());
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status((HttpStatus.BAD_REQUEST)).build() ;
    }

    //PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {

        Article updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

//        //  < 이 로직을 그대로 serive 에 update 메소드로 구현해준다.>
//        // 1. 수정용 entity를 생성
//        Article article = dto.toEntity();
//        log.info("id:{}, article:{}", id, article.toString());
//
//        // 2. 대상 enttity 조회
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
//        if (target == null  || id != article.getId()){
//            // 400, 잘못된 요청 응답!
//            log.info("잘못된 요청! id:{}, article:{}", id, article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        // 4. 업데이트 및 정상 응답 (200)
//        //(일부 정보만 수정할 경우에는 DB에서 조회한 target을 받아온 articleEntity로 붙여줘야 한다.) >> Article(엔티티 클래스)에 patch 메소드 정의해준다.
//        target.patch(article);
//        Article updated = articleRepository.save(target);
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }


    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        //  < 이 로직을 그대로 serive 에 delete 메소드로 구현해준다.>
//        // 대상 찾기
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 잘못된 요청 처리
//        if (target == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        // 대상 삭제
//        articleRepository.delete(target);
//
//        // 데이터 반환

    }

    // 트랜잭션 -> 실패 -> 록백!
    @PostMapping("api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {
        List<Article> createList = articleService.createArticles(dtos);
        return (createList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
