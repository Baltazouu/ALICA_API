package org.alica.api.Dao;
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

    @Column(name = "startDate")
    private String startDate;

    @Column(name = "endDate")
    private String endDate;

    @Column(name = "company")
    private String company;

    @Column(name = "currentJob")
    private boolean currentJob;

    @Override
    public String toString() {
        return "Formation{" +
                "id=" + id +
                ", alumni=" + alumni +
                ", name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", company='" + company + '\'' +
                ", currentJob=" + currentJob +
                '}';
    }

    public void Update(RequestFormationDTO formationDTO) {
        this.name = formationDTO.name();
        this.startDate = formationDTO.startDate();
        this.endDate = formationDTO.endDate();
        this.company = formationDTO.company();
        this.currentJob = formationDTO.currentJob();
    }


}
