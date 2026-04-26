package sk.tope.car_repair_register.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileStorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageService.class);

    @Value("${app.upload.dir:/var/www/uploads}")
    private String uploadDir;

    private Path getTargetDir(Long vehicleId, Long customerId) {
        LOGGER.debug("getTargetDir()");
        Path userPath =
                Path.of(uploadDir).resolve(customerId.toString()).resolve(vehicleId.toString());
        LOGGER.debug("getTargetDir()={}", userPath);
        return userPath;
    }

    public String saveFile(byte[] data, Long vehicleId, Long customerId) throws IOException {
        LOGGER.debug("saveFile({})", data.length);

        String fileName = UUID.randomUUID().toString();

        Path userPath = getTargetDir(vehicleId, customerId);
        Path targetPath = userPath.resolve(fileName);

        Files.createDirectories(targetPath.getParent());

        Files.write(targetPath, data);

        LOGGER.debug("saveFile({})={}", data.length, fileName);

        return fileName;
    }

    public byte[] readFile(String fileName, Long vehicleId, Long customerId) throws IOException {
        return Files.readAllBytes(getTargetDir(vehicleId, customerId).resolve(fileName));
    }

}
