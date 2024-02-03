package org.alica.api.Dto.response;

import lombok.Builder;
import org.alica.api.Enum.Contract;
import org.alica.api.Enum.Level;
import org.alica.api.Enum.Studies;

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
        Contract contract,
        Studies studies,
        Level level,
        String company,
        String contactEmail,
        String contactNumber,
        String companyURL
) { }
