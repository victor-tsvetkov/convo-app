package viktor.tsvetkov.conversations.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import viktor.tsvetkov.conversations.dto.FileDto;
import viktor.tsvetkov.conversations.dto.FileFromDb;
import viktor.tsvetkov.conversations.services.FileEntityService;
import viktor.tsvetkov.conversations.services.FileIOService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {

    private final FileIOService fileIOService;
    private final FileEntityService fileEntityService;

    @PostMapping
    public void uploadFile(@RequestBody MultipartFile multipartFile, UUID idUser, String filename) {
        fileIOService.uploadFile(new FileDto(multipartFile, idUser, filename));
    }

    @GetMapping
    public List<FileFromDb> getFilesByIdUser(@RequestParam(value = "idUser") UUID idUser) {
        return fileEntityService.getFilesByIdUser(idUser);
    }
}
