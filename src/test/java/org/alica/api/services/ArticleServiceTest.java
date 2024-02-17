//package org.alica.api.service;
//
//
//import org.alica.api.dto.response.ResponseArticleDTO;
//import org.alica.api.repository.AlumniRepository;
//import org.alica.api.repository.ArticleRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class ArticleServiceTest {
//
//    @Mock
//    private ArticleRepository articleRepository;
//
//    @Mock
//    private AlumniRepository alumniRepository;
//
//    @InjectMocks
//    private ArticleService articleService;
//
//    @Test
//    void findAll() {
//
//        when(articleRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));
//
//        Page<ResponseArticleDTO> result = articleService.findAll(any(Pageable.class));
//
//        assertEquals(new PageImpl<>(new ArrayList<>()), result);
//
//    }
//
//
//
//
//}
