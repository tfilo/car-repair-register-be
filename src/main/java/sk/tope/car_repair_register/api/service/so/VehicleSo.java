package sk.tope.car_repair_register.api.service.so;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "Vehicle")
public record VehicleSo(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String registrationPlate,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) CustomerSo customer,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true) String vin,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true) String engineCode,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true) String fuelType,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true) Integer enginePower,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true) Integer engineVolume,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true) Integer batteryCapacity,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true) String brand,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true) String model,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true) Integer yearOfManufacture,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) LocalDateTime created,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, nullable = true) LocalDateTime modified
) {
}
