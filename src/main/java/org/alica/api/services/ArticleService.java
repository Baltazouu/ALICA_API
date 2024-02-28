package org.alica.api.services;

import jakarta.persistence.EntityNotFoundException;
import org.alica.api.controllers.AlumniController;
import org.alica.api.controllers.ArticleController;
import org.alica.api.dao.Alumni;
import org.alica.api.dao.Article;
import org.alica.api.dto.request.RequestArticleDTO;
import org.alica.api.dto.response.ResponseArticleDTO;
import org.alica.api.exceptions.UpdateObjectException;
import org.alica.api.mappers.ArticleMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    public void addHateoasLinks(ResponseArticleDTO articleDTO) {


        Link selfLink = linkTo(methodOn(ArticleController.class).findArticleById(articleDTO.getId())).withSelfRel();
        Link alumni = linkTo(methodOn(AlumniController.class).findAlumniById(articleDTO.getAlumniId())).withRel("alumni");
        Link alumniRestricted = linkTo(methodOn(AlumniController.class).findAlumniById(articleDTO.getAlumniId())).withRel("alumniRestricted");

        articleDTO.add(selfLink);
        articleDTO.add(alumni);
        articleDTO.add(alumniRestricted);
    }

    public Page<ResponseArticleDTO> findAll(Pageable page){

        Page<Article> articles = this.articleRepository.findAll(page);

        Page<ResponseArticleDTO> responseArticleDTOS =  articles.map(ARTICLE_MAPPER::mapToResponseArticleDTO);

        responseArticleDTOS.forEach(this::addHateoasLinks);

        return responseArticleDTOS;
    }

    public ResponseArticleDTO findArticleById(UUID id){
        Article article = this.articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(ARTICLE_NOT_FOUND,id)));
        ResponseArticleDTO responseArticleDTO =  ARTICLE_MAPPER.mapToResponseArticleDTO(article);

        addHateoasLinks(responseArticleDTO);

        return responseArticleDTO;
    }

    public ResponseArticleDTO createArticle(RequestArticleDTO article){
        Alumni alumni = this.alumniRepository.findById(article.alumniId()).orElseThrow(() -> new EntityNotFoundException(String.format(ALUMNI_NOT_FOUND,article.alumniId())));
        Article newArticle = ARTICLE_MAPPER.mapToArticle(article,alumni);

        ResponseArticleDTO responseArticleDTO = ARTICLE_MAPPER.mapToResponseArticleDTO(this.articleRepository.save(newArticle));
        addHateoasLinks(responseArticleDTO);

        return responseArticleDTO;
    }

    public ResponseArticleDTO updateArticle(RequestArticleDTO article, UUID id){
        Article articleToUpdate = this.articleRepository.findById(id).orElseThrow(() -> new UpdateObjectException(String.format(ARTICLE_NOT_FOUND,id)));
        Alumni alumni = this.alumniRepository.findById(article.alumniId()).orElseThrow(() -> new UpdateObjectException(String.format(ALUMNI_NOT_FOUND,id)));
        articleToUpdate.setTitle(article.title());
        articleToUpdate.setContent(article.content());
        articleToUpdate.setAlumni(alumni);
        ResponseArticleDTO  responseArticleDTO =  ARTICLE_MAPPER.mapToResponseArticleDTO(this.articleRepository.save(articleToUpdate));

        addHateoasLinks(responseArticleDTO);

        return responseArticleDTO;

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
        Page<ResponseArticleDTO> responseArticleDTOS =  articles.map(ARTICLE_MAPPER::mapToResponseArticleDTO);

        responseArticleDTOS.forEach(this::addHateoasLinks);

        return responseArticleDTOS;
    }

    public Page<ResponseArticleDTO> findByTitleContaining(String title, Pageable page){
        Page<Article> articles = this.articleRepository.findByTitleContaining(title,page);
        Page<ResponseArticleDTO> responseArticleDTOS =  articles.map(ARTICLE_MAPPER::mapToResponseArticleDTO);

        responseArticleDTOS.forEach(this::addHateoasLinks);

        return responseArticleDTOS;
    }



}
