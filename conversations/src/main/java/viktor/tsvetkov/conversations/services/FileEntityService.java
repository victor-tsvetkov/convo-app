package viktor.tsvetkov.conversations.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.FileDto;
import viktor.tsvetkov.conversations.dto.FileFromDb;
import viktor.tsvetkov.conversations.entities.FileEntity;
import viktor.tsvetkov.conversations.repositories.FileEntityRepository;

import static viktor.tsvetkov.conversations.utils.Constants.FilesUrl.FILE_URL;
import static viktor.tsvetkov.conversations.utils.query.SqlQueries.FILES_OF_USER;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileEntityService {

    private final FileEntityRepository repository;
    private final QueryService queryService;

    public void save(FileDto fileDto) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setCreationDate(LocalDateTime.now());
        fileEntity.setFilePath(FILE_URL + fileDto.filename());
        fileEntity.setIdUser(fileDto.idUser());
        repository.save(fileEntity);
    }

    public List<FileFromDb> getFilesByIdUser(UUID idUser) {
        return queryService.executeSql(FILES_OF_USER, FileFromDb.class, Map.of("idUser", idUser));
    }
}
