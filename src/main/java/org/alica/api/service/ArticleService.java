package org.alica.api.service;

import jakarta.persistence.EntityNotFoundException;
import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Article;
import org.alica.api.Dto.request.RequestArticleDTO;
import org.alica.api.Dto.response.ResponseArticleDTO;
import org.alica.api.exception.UpdateObjectException;
import org.alica.api.mapper.ArticleMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final AlumniRepository alumniRepository;

    private static final ArticleMapper ARTICLE_MAPPER = ArticleMapper.INSTANCE;

    public ArticleService(ArticleRepository articleRepository,
                          AlumniRepository alumniRepository) {
        this.articleRepository = articleRepository;
        this.alumniRepository = alumniRepository;
    }

    public Page<ResponseArticleDTO> findAll(Pageable page){

        Page<Article> articles = this.articleRepository.findAll(page);

        return articles.map(ARTICLE_MAPPER::mapToResponseArticleDTO);
    }

    public ResponseArticleDTO findArticleById(UUID id){
        Article article = this.articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Article not found"));
        return ARTICLE_MAPPER.mapToResponseArticleDTO(article);
    }

    public ResponseArticleDTO createArticle(RequestArticleDTO article){
        Alumni alumni = this.alumniRepository.findById(article.alumniId()).orElseThrow(() -> new EntityNotFoundException("Alumni not found"));
        Article newArticle = ARTICLE_MAPPER.mapToArticle(article,alumni);

        return ARTICLE_MAPPER.mapToResponseArticleDTO(this.articleRepository.save(newArticle));
    }

    public ResponseArticleDTO updateArticle(RequestArticleDTO article, UUID id){
        Article articleToUpdate = this.articleRepository.findById(id).orElseThrow(() -> new UpdateObjectException("Article not found"));
        Alumni alumni = this.alumniRepository.findById(article.alumniId()).orElseThrow(() -> new UpdateObjectException("Alumni not found"));
        articleToUpdate.setTitle(article.title());
        articleToUpdate.setContent(article.content());
        articleToUpdate.setAlumni(alumni);
        return ARTICLE_MAPPER.mapToResponseArticleDTO(this.articleRepository.save(articleToUpdate));
    }


    public void deleteArticle(UUID id){

        if(!this.articleRepository.existsById(id)){
            throw new EntityNotFoundException("Article not found");
        }
        this.articleRepository.deleteById(id);
    }


    public Page<ResponseArticleDTO> findArticleByAlumniId(UUID id, Pageable page){
        Alumni alumni = this.alumniRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Alumni not found"));
        Page<Article> articles = this.articleRepository.findByAlumni(alumni,page);
        return articles.map(ARTICLE_MAPPER::mapToResponseArticleDTO);
    }



}
