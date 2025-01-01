package sk.tope.car_repair_register.mapper;

import org.mapstruct.*;
import sk.tope.car_repair_register.api.service.so.VehicleCreateSo;
import sk.tope.car_repair_register.api.service.so.VehicleSo;
import sk.tope.car_repair_register.api.service.so.VehicleUpdateSo;
import sk.tope.car_repair_register.dal.domain.Vehicle;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        uses = {StringTrimmer.class},
        componentModel = "spring")
public interface VehicleMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "modified", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "repairs", ignore = true),
            @Mapping(target = "customer", ignore = true),
            @Mapping(target = "registrationPlate", expression = "java(vehicleCreateSo.registrationPlate().toUpperCase().trim())"),
            @Mapping(target = "vin", expression = "java(vehicleCreateSo.vin() != null ? vehicleCreateSo.vin().toUpperCase().trim() : null)"),
            @Mapping(target = "engineCode", expression = "java(vehicleCreateSo.engineCode() != null ? vehicleCreateSo.engineCode().toUpperCase().trim() : null)")
    })
    Vehicle mapToVehicle(VehicleCreateSo vehicleCreateSo);

    VehicleSo mapToVehicleSo(Vehicle vehicle);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "modified", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "repairs", ignore = true),
            @Mapping(target = "customer", ignore = true),
            @Mapping(target = "registrationPlate", expression = "java(vehicleUpdateSo.registrationPlate().toUpperCase().trim())"),
            @Mapping(target = "vin", expression = "java(vehicleUpdateSo.vin() != null ? vehicleUpdateSo.vin().toUpperCase().trim() : null)"),
            @Mapping(target = "engineCode", expression = "java(vehicleUpdateSo.engineCode() != null ? vehicleUpdateSo.engineCode().toUpperCase().trim() : null)")
    })
    void mapTo(@MappingTarget Vehicle vehicle, VehicleUpdateSo vehicleUpdateSo);
}
