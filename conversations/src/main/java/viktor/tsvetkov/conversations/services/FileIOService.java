package viktor.tsvetkov.conversations.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import viktor.tsvetkov.conversations.dto.FileDto;

import static viktor.tsvetkov.conversations.utils.Constants.FilesUrl.FILE_PATH;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileIOService {

    private final FileEntityService fileEntityService;

    public void uploadFile(FileDto fileDto) {
        if (writeFile(fileDto)) {
            fileEntityService.save(fileDto);
        }
    }

    private boolean writeFile(FileDto fileDto) {
        MultipartFile multipartFile = fileDto.multipartFile();
        if (!multipartFile.isEmpty()) {
            try {
                byte[] bytes = multipartFile.getBytes();
                String filename = fileDto.filename();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(FILE_PATH + filename));
                stream.write(bytes);
                stream.close();
                return true;
            } catch (Exception e) {
                log.error("Произошла ошибка при записи файла {}. Текст ошибки: {}", fileDto.filename(), e.getMessage());
            }
        }
        return false;
    }
}
