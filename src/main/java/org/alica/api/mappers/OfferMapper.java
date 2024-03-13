package org.alica.api.mappers;

import org.alica.api.dao.Alumni;
import org.alica.api.dao.Offer;
import org.alica.api.dto.request.RequestOfferDTO;
import org.alica.api.dto.response.ResponseOfferDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OfferMapper {

    OfferMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(OfferMapper.class);

    @Mapping(target = "alumni",source = "alumni")
    @Mapping(target = "imageId", source = "offerDTO.imageId")
    Offer mapToOffer(RequestOfferDTO offerDTO, Alumni alumni);

    @Mapping(target = "alumniId", source = "alumni.id")
    ResponseOfferDTO mapToResponseOfferDTO(Offer offer);
}
