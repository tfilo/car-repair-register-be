package sk.tope.car_repair_register.api.service.so;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "VehicleCreate")
public record VehicleCreateSo(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @Size(max = 20)
        @NotBlank
        String registrationPlate,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        Long customerId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(max = 20)
        String vin,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(max = 20)
        String engineCode,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(max = 20)
        String fuelType,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Min(0)
        @Max(3000)
        Integer enginePower,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Min(0)
        @Max(10000)
        Integer engineVolume,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Min(0)
        @Max(1000)
        Integer batteryCapacity,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(max = 64)
        String brand,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Size(max = 64)
        String model,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        @Min(1900)
        @Max(2100)
        Integer yearOfManufacture
) {
}
