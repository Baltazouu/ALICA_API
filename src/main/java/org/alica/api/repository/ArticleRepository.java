package org.alica.api.repository;


import org.alica.api.dao.Alumni;
import org.alica.api.dao.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {

    Page<Article> findByAlumni(Alumni alumni, Pageable pageable);

}
