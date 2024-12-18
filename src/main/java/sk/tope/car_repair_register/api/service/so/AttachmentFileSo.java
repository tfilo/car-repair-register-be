package sk.tope.car_repair_register.api.service.so;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.core.io.Resource;

@Schema(name = "AttachmentFile")
public record AttachmentFileSo(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String mimeType,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Resource data
) {
}
