package org.alica.api.dao;

import jakarta.persistence.*;
import lombok.*;
import org.alica.api.Enum.ERole;

@Entity
@Table(name = "Role")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable= false)
    private ERole name;


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
