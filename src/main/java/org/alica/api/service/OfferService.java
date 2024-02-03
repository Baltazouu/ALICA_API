package org.alica.api.service;

import jakarta.el.PropertyNotFoundException;
import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Offer;
import org.alica.api.Dto.request.RequestOfferDTO;
import org.alica.api.Dto.response.ResponseOfferDTO;
import org.alica.api.mapper.OfferMapper;
import org.alica.api.repository.AlumniRepository;
import org.alica.api.repository.OfferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    private final AlumniRepository alumniRepository;

    private final OfferMapper offerMapper = OfferMapper.INSTANCE;

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
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Offer %s Not found !",id)));

        return offerMapper.mapToResponseOfferDTO(offer);
    }

    public ResponseOfferDTO createOffer(RequestOfferDTO requestOfferDTO){

        Alumni alumni = alumniRepository.findById(requestOfferDTO.alumniId()).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni %s Not found !",requestOfferDTO.alumniId())));

        Offer offer = offerMapper.mapToOffer(requestOfferDTO,alumni);

        return offerMapper.mapToResponseOfferDTO(offerRepository.save(offer));
    }


    public ResponseOfferDTO updateOffer(RequestOfferDTO requestOfferDTO, UUID id){

        Offer offer = offerRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Offer %s Not found !",id)));

        Alumni alumni = alumniRepository.findById(requestOfferDTO.alumniId()).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni %s Not found !",requestOfferDTO.alumniId())));

        offer.Update(requestOfferDTO);

        return offerMapper.mapToResponseOfferDTO(offerRepository.save(offer));
    }

    public void deleteOffer(UUID id){
        Offer offer = offerRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Offer %s Not found !",id)));
        offerRepository.deleteById(id);
    }


    public Page<ResponseOfferDTO> findOfferByAlumniId(UUID id, Pageable page) {

        Alumni alumni = alumniRepository.findById(id).orElseThrow(() -> new PropertyNotFoundException(String.format("Alumni %s Not found !",id)));
        Page<Offer> offers = offerRepository.findByAlumni(alumni, page);
        return offers.map(offerMapper::mapToResponseOfferDTO);
    }



}
