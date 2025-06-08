package br.com.project.pi.application.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class ImageUploadService {

    @Value("${aws.api-gateway-url}")
    private String apiGatewayUrl;

    @Value("${aws.s3-bucket-url}")
    private String s3BucketUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String uploadImage(String imageName, String base64Image) {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        String uploadUrl = apiGatewayUrl + imageName;
        String publicS3Url = s3BucketUrl + imageName;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        HttpEntity<byte[]> entity = new HttpEntity<>(imageBytes, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uploadUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return publicS3Url;
        } else {
            throw new RuntimeException("Erro ao fazer upload da imagem: " + response.getStatusCode());
        }
    }
}
