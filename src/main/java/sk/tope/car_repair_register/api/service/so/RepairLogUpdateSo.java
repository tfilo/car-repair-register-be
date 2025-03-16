package sk.tope.car_repair_register.api.service.so;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(name = "RepairLogUpdate")
public record RepairLogUpdateSo(
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @Size(max = 5000)
        @NotBlank
        String content,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        Long vehicleId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED, types = {"integer", "null"})
        Integer odometer,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @PastOrPresent
        LocalDate repairDate
) {
}
