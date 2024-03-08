package org.alica.api.controllers;


import lombok.RequiredArgsConstructor;
import org.alica.api.dto.response.ResponseImageDTO;
import org.alica.api.security.jwt.UserDetailsImpl;
import org.alica.api.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @AuthenticationPrincipal UserDetailsImpl user) throws IOException {
        ResponseImageDTO uploadedImage = imageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedImage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable UUID id) {
        byte[] imageData = imageService.downloadImage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(imageData);
    }
}