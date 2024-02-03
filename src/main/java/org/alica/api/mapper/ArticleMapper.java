package org.alica.api.mapper;

import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Article;
import org.alica.api.Dto.request.RequestArticleDTO;
import org.alica.api.Dto.response.ResponseArticleDTO;
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
