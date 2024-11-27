package viktor.tsvetkov.conversations.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FileUtils {

    public static boolean checkIfPathExists(String path) {
        return Files.exists(Path.of(path));
    }

    public static void createFile(String filename) {
        if (!checkIfPathExists(filename)) {
            try {
                Files.createFile(Path.of(filename));
            } catch (Exception e) {
                log.error("Ошибка при создании файла {}, текст ошибки: {}", filename, e.toString());
            }
        } else {
            log.warn("Файл с именем {} уже существует", filename);
        }
    }

    public static Path getFile(String filename) {
        if (checkIfPathExists(filename)) {
            return Path.of(filename);
        } else {
            return null;
        }
    }

    public static void createDirectory(String directory) {
        if (!checkIfPathExists(directory)) {
            try {
                Files.createDirectory(Path.of(directory));
            } catch (Exception e) {
                log.error("Ошибка при создании директории {}, текст ошибки: {}", directory, e.toString());
            }
        } else {
            log.warn("Директория с именем {} уже существует", directory);
        }
    }

    public static Set<String> getFilesFromDirectory(String directory) {
        try (Stream<Path> stream = Files.list(Path.of(directory))) {
            return stream.filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
