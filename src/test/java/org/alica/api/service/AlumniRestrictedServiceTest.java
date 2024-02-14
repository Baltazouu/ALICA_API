//package org.alica.api.service;
//
//import jakarta.el.PropertyNotFoundException;
//import org.alica.api.dao.Alumni;
//import org.alica.api.dto.response.ResponseAlumniRestricted;
//import org.alica.api.repository.AlumniRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import java.util.ArrayList;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
// class AlumniRestrictedServiceTest {
//
//    @InjectMocks
//    private AlumniRestrictedService alumniRestrictedService;
//
//    @Mock
//    private AlumniRepository alumniRepository;
//
//    @BeforeEach
//    void setUp() {
//        // Initialisation des mocks
//    }
//
//    @Test
//    void testFindAll() {
//        // Given
//        //Page expectedPage = mock(Page.class);
//        Page<Alumni> alumniPage = new PageImpl<>(new ArrayList<>());
//        when(alumniRepository.findAll(any(Pageable.class))).thenReturn(alumniPage);
//       // when(alumniRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);
//
//        // When
//        Page<ResponseAlumniRestricted> result = alumniRestrictedService.findAll(mock(Pageable.class));
//
//        // Then
//        assertEquals(alumniPage, result);
//    }
//
//    @Test
//    void testFindById_AlumniFound() {
//        // Given
//        UUID id = UUID.randomUUID();
//        ResponseAlumniRestricted expectedAlumni = new ResponseAlumniRestricted(id, "John", "Doe", "linkedin.com", "image.jpg");
//        when(alumniRepository.findById(id)).thenReturn(Optional.of(mock(Alumni.class)));
//
//        // When
//        ResponseAlumniRestricted result = alumniRestrictedService.findById(id);
//
//        // Then
//        assertEquals(expectedAlumni, result);
//    }
//
//    @Test
//    void testFindById_AlumniNotFound() {
//        // Given
//        UUID id = UUID.randomUUID();
//        when(alumniRepository.findById(id)).thenReturn(Optional.empty());
//
//        // When / Then
//        assertThrows(PropertyNotFoundException.class, () -> alumniRestrictedService.findById(id));
//    }
//}
