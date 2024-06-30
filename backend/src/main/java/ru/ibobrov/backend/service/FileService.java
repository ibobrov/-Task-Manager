package ru.ibobrov.backend.service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ibobrov.backend.exception.BadRequestException;
import ru.ibobrov.backend.exception.BackendException;

import java.io.*;

@Slf4j
@Service
public class FileService {
    private final String userPhotosDir;

    private final static String JPEG_EXTENSION = "jpeg";
    private final static String JPG_EXTENSION = "jpg";

    public FileService(@Value("${files.user-photos-dir}") String userPhotosDir) {
        this.userPhotosDir = userPhotosDir;
    }

    public void saveUserPhoto(@NotNull MultipartFile multipartFile, @NotNull Long userId) {
        try {
            final String extension = FilenameUtils.getExtension((multipartFile.getOriginalFilename()));

            if (!JPEG_EXTENSION.equals(extension) && !JPG_EXTENSION.equals(extension)) {
                throw new BadRequestException("Файл в неподходящем формате. Необходимо использовать jpg/jpeg");
            }

            final File localFile = new File(userPhotosDir + File.separator + userId + "." + JPEG_EXTENSION);

            if (!localFile.createNewFile() && !localFile.canWrite()) {
                throw new BackendException("Возникла проблема в системе, обратитесь в поддержку");
            }

            writeToLocal(multipartFile, localFile);
        } catch (IOException e) {
            throw new BadRequestException(e);
        }
    }

    private void writeToLocal(@NotNull MultipartFile multipartFile, @NotNull File localFile) throws IOException {

        try (final BufferedInputStream inputStream = new BufferedInputStream(multipartFile.getInputStream(), 1024);
             final BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localFile), 1024)) {

            byte[] buffer = new byte[1024];
            while (inputStream.read(buffer) > -1) {
                outputStream.write(buffer);
            }

            outputStream.flush();
        }
    }
}
