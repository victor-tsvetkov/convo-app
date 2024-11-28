package viktor.tsvetkov.conversations.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import viktor.tsvetkov.conversations.dto.FileDto;
import viktor.tsvetkov.conversations.services.FileIOService;

import java.util.UUID;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {

    private final FileIOService fileIOService;

    @PostMapping
    public void uploadFile(@RequestBody MultipartFile multipartFile, UUID idUser, String filename) {
        fileIOService.uploadFile(new FileDto(multipartFile, idUser, filename));
    }
}
