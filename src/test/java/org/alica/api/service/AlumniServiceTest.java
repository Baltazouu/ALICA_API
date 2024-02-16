package org.alica.api.service;


import org.alica.api.dao.Alumni;
import org.alica.api.dto.request.RequestAlumniDTO;
import org.alica.api.dto.response.ResponseAlumniDTO;
import org.alica.api.mapper.AlumniMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.repository.RoleRepository;
import org.hibernate.PropertyNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlumniServiceTest {

    @Mock
    private AlumniRepository alumniRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AlumniService alumniService;

    private final AlumniMapper ALUMNI_MAPPER = AlumniMapper.INSTANCE;


    Alumni alumni = Alumni.builder().email("email")
            .id(UUID.randomUUID())
            .firstName("JEAN")
            .lastName("DUPONT")
            .imageURL("imageURL.png")
            .linkedinURL("linkedinURL")
            .entryYear("2015")
            .password("password")
            .roles(new HashSet<>())
            .build();


    RequestAlumniDTO requestAlumniDTO = RequestAlumniDTO.builder()
            .email("email")
            .firstName("JEAN")
            .lastName("DUPONT")
            .imageURL("imageURL.png")
            .linkedinURL("linkedinURL")
            .entryYear("2015")
            .build();
    @Test
    void testFindAllShouldSucceed(){

        //Page expectedPage = mock(Page.class);
        when(alumniRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));


        Page<ResponseAlumniDTO> result = alumniService.findAll(mock(Pageable.class));

        assertEquals(new PageImpl<>(new ArrayList<>()), result);

    }

    @Test
    void testFindAlumniByEmailShouldSucceed(){



        ResponseAlumniDTO response = ALUMNI_MAPPER.mapResponseAlumniDTO(alumni);

        when(alumniRepository.findByEmail(any())).thenReturn(java.util.Optional.of(alumni));

        ResponseAlumniDTO result = alumniService.findAlumniByEmail(alumni.getEmail());

        assertEquals(result, response);

    }

    @Test
    void testFindAlumniByEmailWithInvalidEmail(){

        when(alumniRepository.findByEmail(any())).thenReturn(java.util.Optional.empty());

        assertThrows(PropertyNotFoundException.class, () -> alumniService.findAlumniByEmail("invalidEmail"));

    }

    @Test
    void testUpdateAlumniShouldSucceed(){

            UUID id = UUID.randomUUID();
            ResponseAlumniDTO response = ALUMNI_MAPPER.mapResponseAlumniDTO(alumni);

            when(alumniRepository.findById(id)).thenReturn(java.util.Optional.of(alumni));
            when(alumniRepository.save(alumni)).thenReturn(alumni);

            ResponseAlumniDTO result = alumniService.updateAlumni(requestAlumniDTO, id);

            assertEquals(result, response);
    }

    @Test
    void testUpdateAlumniWithInvalidId(){

        UUID id = UUID.randomUUID();

        when(alumniRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(PropertyNotFoundException.class, () -> alumniService.updateAlumni(requestAlumniDTO, id));

    }

    @Test
    void testFindAlumniByIdShouldSucceed(){

        UUID id = UUID.randomUUID();
        ResponseAlumniDTO response = ALUMNI_MAPPER.mapResponseAlumniDTO(alumni);

        when(alumniRepository.findById(id)).thenReturn(java.util.Optional.of(alumni));

        ResponseAlumniDTO result = alumniService.findAlumniById(id);

        assertEquals(result, response);

    }

    @Test
    void testFindAlumniByIdWithInvalidId(){

        UUID id = UUID.randomUUID();

        when(alumniRepository.findById(id)).thenReturn(java.util.Optional.empty());

        assertThrows(PropertyNotFoundException.class, () -> alumniService.findAlumniById(id));

    }

    @Test
    void testDeleteAlumniShouldSucceed(){

        UUID id = UUID.randomUUID();

        when(alumniRepository.existsById(id)).thenReturn(true);

        alumniService.deleteAlumni(id);

    }

    @Test
    void testDeleteAlumniWithInvalidId(){

        UUID id = UUID.randomUUID();

        when(alumniRepository.existsById(id)).thenReturn(false);

        assertThrows(PropertyNotFoundException.class, () -> alumniService.deleteAlumni(id));

    }


    @Test
    void testLoadByUsernameShouldSucceed() {
        // Given
        when(alumniRepository.findByEmail(alumni.getEmail())).thenReturn(Optional.of(alumni));
        // When
        UserDetails userDetails = alumniService.loadUserByUsername(alumni.getEmail());
        // Then
        assertEquals(alumni.getEmail(), userDetails.getUsername());
    }

    @Test
    void testLoadByUsernameWithInvalidEmail() {
        // Given
        when(alumniRepository.findByEmail(alumni.getEmail())).thenReturn(Optional.empty());
        // When / Then
        assertThrows(UsernameNotFoundException.class, () -> alumniService.loadUserByUsername(alumni.getEmail()));
    }

    @Test
    void testLoadByUsernameWithNullEmail() {
        // Given
        when(alumniRepository.findByEmail(null)).thenReturn(Optional.empty());
        // When / Then
        assertThrows(UsernameNotFoundException.class, () -> alumniService.loadUserByUsername(null));
    }

}
