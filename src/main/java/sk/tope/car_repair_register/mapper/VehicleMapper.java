package sk.tope.car_repair_register.mapper;

import org.mapstruct.*;
import sk.tope.car_repair_register.api.service.so.VehicleCreateSo;
import sk.tope.car_repair_register.api.service.so.VehicleSo;
import sk.tope.car_repair_register.api.service.so.VehicleUpdateSo;
import sk.tope.car_repair_register.dal.domain.Vehicle;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface VehicleMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "modified", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "repairs", ignore = true),
            @Mapping(target = "customer", ignore = true)
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
            @Mapping(target = "customer", ignore = true)
    })
    void mapTo(@MappingTarget Vehicle vehicle, VehicleUpdateSo vehicleUpdateSo);
}
