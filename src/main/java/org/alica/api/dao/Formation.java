package org.alica.api.dao;
import jakarta.persistence.*;
import lombok.*;
import org.alica.api.dto.request.RequestFormationDTO;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Formation")
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumni_id")
    private Alumni alumni;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "company")
    private String company;

    @Column(name = "current_Job")
    private boolean currentJob;

    @Override
    public String toString() {
        return "Formation{" +
                "id=" + id +
                ", alumni=" + alumni +
                ", firstName='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", company='" + company + '\'' +
                ", currentJob=" + currentJob +
                '}';
    }

    public void update(RequestFormationDTO formationDTO) {
        this.name = formationDTO.name();
        this.startDate = formationDTO.startDate();
        this.endDate = formationDTO.endDate();
        this.company = formationDTO.company();
        this.currentJob = formationDTO.currentJob();
    }


}
