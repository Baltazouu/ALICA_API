package org.alica.api.dto.response;

import lombok.Builder;
import org.alica.api.enums.EContract;
import org.alica.api.enums.ELevel;
import org.alica.api.enums.EStudies;

import java.util.UUID;

@Builder
public record ResponseOfferDTO(
        UUID id,
        UUID alumniId,
        String title,
        String description,
        String jobDescription,
        String experienceRequired,
        String city,
        EContract EContract,
        EStudies EStudies,
        ELevel ELevel,
        String company,
        String contactEmail,
        String contactNumber,
        String companyURL
) { }
