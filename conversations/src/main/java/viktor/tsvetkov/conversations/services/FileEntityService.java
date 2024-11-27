package viktor.tsvetkov.conversations.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.FileDto;
import viktor.tsvetkov.conversations.entities.FileEntity;
import viktor.tsvetkov.conversations.repositories.FileEntityRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileEntityService {

    private final FileEntityRepository repository;

    public void save(FileDto fileDto) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setCreationDate(LocalDateTime.now());
        fileEntity.setFilePath(fileDto.filename());
        fileEntity.setIdUser(fileDto.idUser());
        repository.save(fileEntity);
    }
}
