package sk.tope.car_repair_register.api.service.so;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "Vehicle")
public record VehicleSo(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String registrationPlate,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) CustomerSo customer,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"}) String vin,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"}) String engineCode,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"}) String fuelType,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"integer", "null"}) Integer enginePower,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"integer", "null"}) Integer engineVolume,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"integer", "null"}) Integer batteryCapacity,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"}) String brand,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"}) String model,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"integer", "null"}) Integer yearOfManufacture,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) LocalDateTime created,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"string", "null"}) LocalDateTime modified
) {
}
