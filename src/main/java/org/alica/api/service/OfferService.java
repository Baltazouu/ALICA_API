package org.alica.api.service;

import jakarta.el.PropertyNotFoundException;
import org.alica.api.dao.Alumni;
import org.alica.api.dao.Offer;
import org.alica.api.dto.request.RequestOfferDTO;
import org.alica.api.dto.response.ResponseOfferDTO;
import org.alica.api.mapper.OfferMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.repository.OfferRepository;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    private final AlumniRepository alumniRepository;

    private static final String OFFER_NOT_FOUND = "Offer %s Not found !";

    private static final String ALUMNI_NOT_FOUND = "Alumni %s Not found !";

    private static final OfferMapper offerMapper = OfferMapper.INSTANCE;

    OfferService(OfferRepository offerRepository,
                 AlumniRepository alumniRepository) {
        this.offerRepository = offerRepository;
        this.alumniRepository = alumniRepository;
    }

    public Page<ResponseOfferDTO> findAll(Pageable p){

        Page<Offer> offers =  this.offerRepository.findAll(p);

        return offers.map(offerMapper::mapToResponseOfferDTO);
    }

    public ResponseOfferDTO findOfferById(UUID id){
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format(OFFER_NOT_FOUND,id)));

        return offerMapper.mapToResponseOfferDTO(offer);
    }

    public ResponseOfferDTO createOffer(RequestOfferDTO requestOfferDTO,UserDetailsImpl user){

        Alumni alumni = alumniRepository.findById(user.getId()).orElseThrow(() -> new PropertyNotFoundException(String.format(ALUMNI_NOT_FOUND,requestOfferDTO.alumniId())));

        Offer offer = offerMapper.mapToOffer(requestOfferDTO,alumni);

        offer = offerRepository.save(offer);

        return offerMapper.mapToResponseOfferDTO(offer);
    }


    public ResponseOfferDTO updateOffer(RequestOfferDTO requestOfferDTO, UUID id, UserDetailsImpl user){

        Offer offer = offerRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format(OFFER_NOT_FOUND,id)));

        if(offer.getAlumni().getId() != user.getId()) throw new PropertyNotFoundException("You are not allowed to update this offer !");

        offer.Update(requestOfferDTO);
        return offerMapper.mapToResponseOfferDTO(offerRepository.save(offer));
    }

    public void deleteOffer(UUID id, UserDetailsImpl user){

        Offer offer = offerRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format(OFFER_NOT_FOUND,id)));

        if(offer.getAlumni().getId() != user.getId()) throw new PropertyNotFoundException("You are not allowed to delete this offer !");
        offerRepository.deleteById(id);
    }


    public Page<ResponseOfferDTO> findOfferByAlumniId(UUID id, Pageable page) {

        Alumni alumni = alumniRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format(ALUMNI_NOT_FOUND,id)));
        Page<Offer> offers = offerRepository.findByAlumni(alumni, page);
        return offers.map(offerMapper::mapToResponseOfferDTO);
    }

}
