package org.alica.api.Dto.response;

import org.alica.api.Enum.Contract;
import org.alica.api.Enum.Level;
import org.alica.api.Enum.Studies;

import java.util.UUID;

public record ResponseOfferDTO(
        UUID id,
        String title,
        String description,
        String jobDescription,
        String experienceRequired,
        String city,
        Contract contract,
        Studies studies,
        Level level,
        String company,
        String email,
        String contactNumber,
        String website
) { }
