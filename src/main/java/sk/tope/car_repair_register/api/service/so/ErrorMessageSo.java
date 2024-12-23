package sk.tope.car_repair_register.api.service.so;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.util.List;

@Schema(name = "ErrorMessage")
public record ErrorMessageSo(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        HttpStatus httpStatus,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        String message,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        List<FieldErrorSo> fieldError
) {
};
