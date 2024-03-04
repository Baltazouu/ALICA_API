package org.alica.api.controllers;

import jakarta.validation.Valid;
import org.alica.api.dto.request.RequestArticleDTO;
import org.alica.api.dto.response.ResponseArticleDTO;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.alica.api.services.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<ResponseArticleDTO> findAll(@PageableDefault Pageable page, @RequestParam(required = false) Optional<String> title){
        if(title.isPresent())
            return this.articleService.findByTitleContaining(title.get(),page);
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

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseArticleDTO createArticle(@Valid @RequestBody RequestArticleDTO article,@AuthenticationPrincipal UserDetailsImpl user){
        return this.articleService.createArticle(article,user.getId());
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @PutMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseArticleDTO updateArticle(@Valid @RequestBody RequestArticleDTO article, @PathVariable UUID id, @AuthenticationPrincipal UserDetailsImpl user){
        return this.articleService.updateArticle(article,id,user.getId());
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable UUID id){
        this.articleService.deleteArticle(id);
    }
}
