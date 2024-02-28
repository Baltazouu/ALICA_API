package org.alica.api.dto.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseArticleDTO extends RepresentationModel<ResponseArticleDTO> {

    private UUID id;
    private UUID alumniId;
    private String title;
    private String subtitle;
    private String content;
    private String imgURL;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ResponseArticleDTO that = (ResponseArticleDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(alumniId, that.alumniId) && Objects.equals(title, that.title) && Objects.equals(subtitle, that.subtitle) && Objects.equals(content, that.content) && Objects.equals(imgURL, that.imgURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, alumniId, title, subtitle, content, imgURL);
    }
}
