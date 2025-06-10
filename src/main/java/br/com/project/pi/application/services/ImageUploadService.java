package br.com.project.pi.application.services;

import br.com.project.pi.application.exception.ImageUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Locale;

@Service
public class ImageUploadService {

    @Value("${aws.api-gateway-url}")
    private String apiGatewayUrl;

    @Value("${aws.s3-bucket-url}")
    private String s3BucketUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<String> uploadImages(String baseName, List<String> base64Images) {
        List<String> uploadedUrls = new ArrayList<>();

        for (String base64Image : base64Images) {
            try {
                String extension = determineImageExtension(base64Image);
                String uniqueFileName = generateUniqueFileName(baseName, extension);

                String url = uploadSingleImage(uniqueFileName, base64Image);
                uploadedUrls.add(url);
            } catch (Exception e) {
                throw new ImageUploadException();
            }
        }

        return uploadedUrls;
    }

    private String uploadSingleImage(String imageName, String base64Image) {
        String base64Data = base64Image;
        if (base64Image.contains(",")) {
            base64Data = base64Image.split(",")[1];
        }

        byte[] imageBytes = Base64.getDecoder().decode(base64Data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.add("x-amz-meta-content-type", getContentTypeFromExtension(imageName));
        headers.add("x-amz-acl", "public-read");

        String uploadUrl = apiGatewayUrl + imageName;
        String publicS3Url = s3BucketUrl + imageName;

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
            throw new ImageUploadException();
        }
    }

    public String generateUniqueFileName(String baseName, String extension) {
        String timestamp = String.valueOf(Instant.now().getEpochSecond());
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String sanitized = sanitizeFileName(baseName);

        return String.format("%s-%s-%s.%s",
                timestamp,
                uniqueId,
                sanitized,
                extension);
    }

    private String determineImageExtension(String base64Image) {
        if (base64Image.startsWith("data:image/jpeg")) {
            return "jpg";
        } else if (base64Image.startsWith("data:image/png")) {
            return "png";
        } else if (base64Image.startsWith("data:image/gif")) {
            return "gif";
        } else if (base64Image.startsWith("data:image/webp")) {
            return "webp";
        }
        return "png";
    }

    private String getContentTypeFromExtension(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        } else if (fileName.endsWith(".webp")) {
            return "image/webp";
        }
        return "image/png";
    }

    public String sanitizeFileName(String fileName) {
        String normalized = Normalizer.normalize(fileName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        return normalized.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }
}