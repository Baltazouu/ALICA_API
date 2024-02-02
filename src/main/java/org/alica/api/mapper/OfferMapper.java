package org.alica.api.mapper;

import org.alica.api.Dao.Alumni;
import org.alica.api.Dao.Offer;
import org.alica.api.Dto.request.RequestOfferDTO;
import org.alica.api.Dto.response.ResponseOfferDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OfferMapper {

    OfferMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(OfferMapper.class);

    @Mapping(target = "alumni",source = "alumni")
    Offer mapToOffer(RequestOfferDTO offerDTO, Alumni alumni);

    ResponseOfferDTO mapToResponseOfferDTO(Offer offer);
}