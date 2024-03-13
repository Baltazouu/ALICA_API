package org.alica.api.services;

import jakarta.el.PropertyNotFoundException;
import org.alica.api.dao.Alumni;
import org.alica.api.dto.response.ResponseAlumniRestrictedDTO;
import org.alica.api.mappers.AlumniMapper;
import org.alica.api.repository.AlumniRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class AlumniRestrictedServiceTest {

    @Mock
    private AlumniRepository alumniRepository;

    @InjectMocks
    private AlumniRestrictedService alumniRestrictedService;

    private static final AlumniMapper ALUMNI_MAPPER = AlumniMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        // Initialisation des mocks
    }

    @Test
    void testFindAll() {
        // Given
        //Page expectedPage = mock(Page.class);
        Page<Alumni> alumniPage = new PageImpl<>(new ArrayList<>());
        when(alumniRepository.findAll(any(Pageable.class))).thenReturn(alumniPage);
       // when(alumniRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // When
        Page<ResponseAlumniRestrictedDTO> result = alumniRestrictedService.findAll(mock(Pageable.class));

        // Then
        assertEquals(alumniPage, result);
    }

    @Test
    void testFindById_AlumniFound() {
        // Given
        UUID id = UUID.randomUUID();
        ResponseAlumniRestrictedDTO expectedAlumni = new ResponseAlumniRestrictedDTO(id, "John", "Doe", "linkedin.com", UUID.randomUUID());
        when(alumniRepository.findById(id)).thenReturn(Optional.of(Alumni.builder().id(id).firstName("John").lastName("Doe").linkedinURL("linkedin.com").imageId(UUID.randomUUID()).roles(new HashSet<>()).build()));
        // When
        ResponseAlumniRestrictedDTO result = alumniRestrictedService.findById(id);

        // Then
        assertEquals(expectedAlumni.getId(), result.getId());
    }


    @Test
    void testFindById_AlumniNotFound() {
        // Given
        UUID id = UUID.randomUUID();
        when(alumniRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(PropertyNotFoundException.class, () -> alumniRestrictedService.findById(id));
    }
}
