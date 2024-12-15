package sk.tope.car_repair_register.api.service.so;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(name = "CustomerCreate")
public record CustomerCreateSo(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @Size(max = 64)
        @NotEmpty
        String name,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(max = 64)
        @NotEmpty
        String surname,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(max = 20)
        @NotEmpty
        String mobile,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(max = 320)
        @NotEmpty
        String email) {
}
