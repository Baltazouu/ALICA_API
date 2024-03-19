
package org.alica.api.services;

import lombok.RequiredArgsConstructor;
import org.alica.api.dao.Image;
import org.alica.api.dto.response.ResponseImageDTO;
import org.alica.api.repository.ImageRepository;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public ResponseImageDTO uploadImage(MultipartFile imageFile) throws IOException {
        byte[] imageData = imageFile.getBytes();
        String base64Image = Base64.getEncoder().encodeToString(imageData);

        var imageToSave = Image.builder()
                .name(imageFile.getOriginalFilename())
                .type(imageFile.getContentType())
                .imageData(base64Image)  // Enregistrer l'image encodée en base64
                .build();


        Image img = imageRepository.save(imageToSave);


        return ResponseImageDTO.builder()
                .id(img.getId())
                .name(img.getName())
                .type(img.getType())
                .build();
    }

    public byte[] downloadImage(UUID id) {
        Optional<Image> dbImage = imageRepository.findById(id);

        return dbImage.map(image -> {
            try {
                return Base64.getDecoder().decode(image.getImageData());
            } catch (Exception exception) {
                throw new ContextedRuntimeException("Error downloading an image", exception)
                        .addContextValue("Image ID",  image.getId())
                        .addContextValue("Image name", image.getName());
            }
        }).orElse(null);
    }

}