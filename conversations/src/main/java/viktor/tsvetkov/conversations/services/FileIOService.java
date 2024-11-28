package viktor.tsvetkov.conversations.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import viktor.tsvetkov.conversations.dto.FileDto;

import static viktor.tsvetkov.conversations.utils.Constants.FilesUrl.FILE_PATH;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

@Service
@RequiredArgsConstructor
public class FileIOService {

    private final FileEntityService fileEntityService;

    public void uploadFile(FileDto fileDto) {
        MultipartFile multipartFile = fileDto.multipartFile();
        if (!multipartFile.isEmpty()) {
            try {
                byte[] bytes = multipartFile.getBytes();
                String filename = fileDto.filename();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(FILE_PATH + filename));
                stream.write(bytes);
                stream.close();
                fileEntityService.save(fileDto);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
