package sk.tope.car_repair_register.api.service.so;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "Customer")
public record CustomerSo(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"}) String surname,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"}) String mobile,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"}) String email,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) LocalDateTime created,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"}) LocalDateTime modified
) {
}
