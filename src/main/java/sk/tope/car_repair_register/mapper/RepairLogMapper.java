package sk.tope.car_repair_register.mapper;

import org.mapstruct.*;
import sk.tope.car_repair_register.api.service.so.RepairLogCreateSo;
import sk.tope.car_repair_register.api.service.so.RepairLogSo;
import sk.tope.car_repair_register.api.service.so.RepairLogUpdateSo;
import sk.tope.car_repair_register.dal.domain.RepairLog;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface RepairLogMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "modified", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "attachments", ignore = true)
    })
    RepairLog mapToRepairLog(RepairLogCreateSo repairLogCreateSo);

    RepairLogSo mapToRepairLogSo(RepairLog repairLog);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "modified", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "attachments", ignore = true)
    })
    void mapTo(@MappingTarget RepairLog repairLog, RepairLogUpdateSo repairLogUpdateSo);
}
