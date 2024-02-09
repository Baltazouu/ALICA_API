package org.alica.api.dao;
import jakarta.persistence.*;
import lombok.*;
import org.alica.api.dto.request.RequestArticleDTO;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "content")
    private String content;

    @Column(name = "imgURL")
    private String imgURL;

    @ManyToOne
    @JoinColumn(name = "alumni_id")
    private Alumni alumni;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", content='" + content + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", alumni=" + alumni +
                '}';
    }

    public void update(RequestArticleDTO articleDTO) {
        this.title = articleDTO.title();
        this.subtitle = articleDTO.subtitle();
        this.content = articleDTO.content();
        this.imgURL = articleDTO.imgURL();
    }



}
