package org.alica.api.dto.response;

import lombok.Builder;
import org.alica.api.Enum.EContract;
import org.alica.api.Enum.ELevel;
import org.alica.api.Enum.EStudies;

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
