package org.alica.api.dao;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alica.api.dto.request.RequestExperienceDTO;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Experience")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Alumni alumni;

    private String title;

    private Date startDate;

    private Date endDate;

    private String companyName;

    private boolean isCurrent;


    public void update(RequestExperienceDTO requestExperienceDTO){
        this.title = requestExperienceDTO.title();
        this.startDate = requestExperienceDTO.startDate();
        this.endDate = requestExperienceDTO.endDate();
        this.companyName = requestExperienceDTO.companyName();
        this.isCurrent = requestExperienceDTO.current();
    }
}
