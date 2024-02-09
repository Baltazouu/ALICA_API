package org.alica.api.mapper;

import org.alica.api.dao.Alumni;
import org.alica.api.dao.Article;
import org.alica.api.dto.request.RequestArticleDTO;
import org.alica.api.dto.response.ResponseArticleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleMapper {

    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    @Mapping(target = "alumni", source = "alumni")
    Article mapToArticle(RequestArticleDTO requestArticleDTO, Alumni alumni);

    @Mapping(target = "alumniId", source = "alumni.id")
    ResponseArticleDTO mapToResponseArticleDTO(Article article);

}
