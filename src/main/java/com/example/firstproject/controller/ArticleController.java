package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // 1. id로 데이터를 가져온다
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);
        // 3. 보여줄 페이지를 설정해준다
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {

        // 1. 모든 Aticle을 가져온다.
       List<Article> articleEntityList = articleRepository.findAll();

        // 2. 가져온 Article 묶음을 뷰로 전달
        model.addAttribute("articleList",articleEntityList);

        // 3. 뷰 페이지 설정해준다.
       return "articles/index";  // >> articles/index.mustache로 이동된다
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        // (DB에 있는)수정할 데이터를 가져와야한다.
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터를 등록해준다.
        model.addAttribute("article",articleEntity);

        // 수정하는 뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        // 1. DTO를 Entity로 변환한다
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2. Entity를 DB로 저장한다.
        // 2-1 : DB에 기존 데이터를 가져온다.
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2 : 기존 데이터에 값을 생신한다.
        if (target != null) {
            articleRepository.save(articleEntity);  // Entity가 DB로 갱신된다.
        }

        // 3. 수정 결과 페이지로 리다이렉트 한다.
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제요청이 들어왔습니다.");

        // 1. 삭제 대상을 가져온다.
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        // 2. 대상을 삭제한다.
        if (target != null ){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제가 완료되었습니다!");
        }

        // 3. 결과 페이지로 리다이렉트한다.
        return "redirect:/articles";
    }
}