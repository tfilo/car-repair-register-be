package sk.tope.car_repair_register.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sk.tope.car_repair_register.api.service.so.RepairLogCreateSo;
import sk.tope.car_repair_register.api.service.so.RepairLogSo;
import sk.tope.car_repair_register.api.service.so.RepairLogUpdateSo;
import sk.tope.car_repair_register.bundle.ErrorBundle;
import sk.tope.car_repair_register.dal.domain.RepairLog;
import sk.tope.car_repair_register.dal.domain.Vehicle;
import sk.tope.car_repair_register.dal.repository.RepairLogRepository;
import sk.tope.car_repair_register.dal.repository.VehicleRepository;
import sk.tope.car_repair_register.dal.specification.RepairLogSpecification;
import sk.tope.car_repair_register.mapper.RepairLogMapper;

@Service
public class RepairLogApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepairLogApiService.class);

    private RepairLogMapper repairLogMapper;

    private RepairLogRepository repairLogRepository;

    private VehicleRepository vehicleRepository;

    @Autowired
    public void setRepairLogMapper(RepairLogMapper repairLogMapper) {
        this.repairLogMapper = repairLogMapper;
    }

    @Autowired
    public void setRepairLogRepository(RepairLogRepository repairLogRepository) {
        this.repairLogRepository = repairLogRepository;
    }

    @Autowired
    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Page<RepairLogSo> find(String query, Long vehicleId, Pageable pageable) {
        LOGGER.debug("find({},{},{})", query, vehicleId, pageable);
        Page<RepairLog> result = repairLogRepository.findAll(new RepairLogSpecification(query, vehicleId), pageable);
        LOGGER.debug("find({},{},{})={}", query, vehicleId, pageable, result);
        return result.map(rl -> repairLogMapper.mapToRepairLogSo(rl));
    }

    public RepairLogSo get(Long id) {
        LOGGER.debug("get({})", id);
        RepairLog result = repairLogRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.REPAIR_LOG_NOT_FOUND.name()));
        LOGGER.debug("get({})={}", id, result);
        return repairLogMapper.mapToRepairLogSo(result);
    }

    @Transactional
    public RepairLogSo create(RepairLogCreateSo repairLogCreateSo) {
        LOGGER.debug("create({})", repairLogCreateSo);
        RepairLog repairLog = repairLogMapper.mapToRepairLog(repairLogCreateSo);
        Vehicle vehicle = vehicleRepository.findById(repairLogCreateSo.vehicleId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.VEHICLE_NOT_FOUND.name()));
        repairLog.setVehicle(vehicle);
        repairLog = repairLogRepository.save(repairLog);
        LOGGER.debug("create({})={}", repairLogCreateSo, repairLog);
        return repairLogMapper.mapToRepairLogSo(repairLog);
    }

    @Transactional
    public RepairLogSo update(Long id, RepairLogUpdateSo repairLogUpdateSo) {
        LOGGER.debug("update({})", repairLogUpdateSo);
        RepairLog repairLog = repairLogRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.REPAIR_LOG_NOT_FOUND.name()));
        repairLogMapper.mapTo(repairLog, repairLogUpdateSo);
        if (!repairLog.getVehicle().getId().equals(repairLogUpdateSo.vehicleId())) {
            Vehicle vehicle = vehicleRepository.findById(repairLogUpdateSo.vehicleId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.VEHICLE_NOT_FOUND.name()));
            repairLog.setVehicle(vehicle);
        }
        repairLog = repairLogRepository.save(repairLog);
        return repairLogMapper.mapToRepairLogSo(repairLog);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.debug("delete({})", id);
        RepairLog repairLog = repairLogRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.REPAIR_LOG_NOT_FOUND.name()));
        repairLogRepository.delete(repairLog);
    }
}
