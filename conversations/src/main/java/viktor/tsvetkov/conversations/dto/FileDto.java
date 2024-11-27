package viktor.tsvetkov.conversations.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record FileDto(
        MultipartFile multipartFile,
        UUID idUser,
        String filename
) {
}
