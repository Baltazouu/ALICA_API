package org.alica.api.service;

import jakarta.persistence.EntityNotFoundException;
import org.alica.api.dao.Alumni;
import org.alica.api.dao.Article;
import org.alica.api.dto.request.RequestArticleDTO;
import org.alica.api.dto.response.ResponseArticleDTO;
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

    private static final String ARTICLE_NOT_FOUND = "Article %s Not found !";

    private static final String ALUMNI_NOT_FOUND = "Alumni %s Not found !";

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
        Article article = this.articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(ARTICLE_NOT_FOUND,id)));
        return ARTICLE_MAPPER.mapToResponseArticleDTO(article);
    }

    public ResponseArticleDTO createArticle(RequestArticleDTO article){
        Alumni alumni = this.alumniRepository.findById(article.alumniId()).orElseThrow(() -> new EntityNotFoundException(String.format(ALUMNI_NOT_FOUND,article.alumniId())));
        Article newArticle = ARTICLE_MAPPER.mapToArticle(article,alumni);

        return ARTICLE_MAPPER.mapToResponseArticleDTO(this.articleRepository.save(newArticle));
    }

    public ResponseArticleDTO updateArticle(RequestArticleDTO article, UUID id){
        Article articleToUpdate = this.articleRepository.findById(id).orElseThrow(() -> new UpdateObjectException(String.format(ARTICLE_NOT_FOUND,id)));
        Alumni alumni = this.alumniRepository.findById(article.alumniId()).orElseThrow(() -> new UpdateObjectException(String.format(ALUMNI_NOT_FOUND,id)));
        articleToUpdate.setTitle(article.title());
        articleToUpdate.setContent(article.content());
        articleToUpdate.setAlumni(alumni);
        return ARTICLE_MAPPER.mapToResponseArticleDTO(this.articleRepository.save(articleToUpdate));
    }


    public void deleteArticle(UUID id){

        if(!this.articleRepository.existsById(id)){
            throw new EntityNotFoundException(String.format(ARTICLE_NOT_FOUND,id));
        }
        this.articleRepository.deleteById(id);
    }


    public Page<ResponseArticleDTO> findArticleByAlumniId(UUID id, Pageable page){
        Alumni alumni = this.alumniRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(ALUMNI_NOT_FOUND,id)));
        Page<Article> articles = this.articleRepository.findByAlumni(alumni,page);
        return articles.map(ARTICLE_MAPPER::mapToResponseArticleDTO);
    }



}
