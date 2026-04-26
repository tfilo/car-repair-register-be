package sk.tope.car_repair_register.api.service;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttachmentMigrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentMigrationService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FileStorageService fileStorageService;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void migrateToFilesystem() {
        String sql =
                """
                        SELECT f.data as data, a.id as attachment_id, v.id as vehicle_id, c.id as customer_id FROM attachment a
                            JOIN file f ON a.id = f.id
                            JOIN repair_log rl ON rl.id = a.repair_log_id
                            JOIN vehicle v ON v.id = rl.vehicle_id
                            JOIN customer c ON c.id = v.customer_id
                            WHERE a.file_name IS NULL
                        """;

        jdbcTemplate.query(sql, (rs) -> {
            byte[] data = rs.getBytes("data");
            Long attachmentId = rs.getLong("attachment_id");
            Long vehicleId = rs.getLong("vehicle_id");
            Long customerId = rs.getLong("customer_id");

            LOGGER.debug("Migrating data for attachment id: {}, vehicle id: {}, customer id: {}",
                    attachmentId, vehicleId, customerId);

            try {
                String newFileName = fileStorageService.saveFile(data, vehicleId, customerId);

                jdbcTemplate.update("UPDATE attachment SET file_name = ? WHERE id = ?", newFileName, attachmentId);
                jdbcTemplate.update("DELETE FROM file WHERE id = ?", attachmentId);
            } catch (IOException e) {
                throw new RuntimeException("Chyba pri migrácii ID: " + attachmentId, e);
            }
        });

    }
}
