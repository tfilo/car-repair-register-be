package sk.tope.car_repair_register.api.service.so;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FieldError")
public record FieldErrorSo(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String fieldName,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String errorMessage
) {
}
