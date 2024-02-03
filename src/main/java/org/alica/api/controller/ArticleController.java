package org.alica.api.controller;
import jakarta.validation.Valid;
import org.alica.api.Dto.request.RequestArticleDTO;
import org.alica.api.Dto.response.ResponseArticleDTO;
import org.alica.api.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseArticleDTO> findAll(@PageableDefault Pageable page){
        return this.articleService.findAll(page);
    }

    @GetMapping("/alumni/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseArticleDTO> findArticleByAlumniId(@PathVariable UUID id, @PageableDefault Pageable page){
        return this.articleService.findArticleByAlumniId(id,page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseArticleDTO findArticleById(@PathVariable UUID id){
         return this.articleService.findArticleById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseArticleDTO createArticle(@Valid @RequestBody RequestArticleDTO article){
        return this.articleService.createArticle(article);
    }

    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseArticleDTO updateArticle(@Valid @RequestBody RequestArticleDTO article, @PathVariable UUID id){
        return this.articleService.updateArticle(article,id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable UUID id){
        this.articleService.deleteArticle(id);
    }
}
