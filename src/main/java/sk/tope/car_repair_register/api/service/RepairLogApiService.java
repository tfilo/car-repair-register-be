package sk.tope.car_repair_register.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.tope.car_repair_register.api.service.so.RepairLogCreateSo;
import sk.tope.car_repair_register.api.service.so.RepairLogSo;
import sk.tope.car_repair_register.api.service.so.RepairLogUpdateSo;
import sk.tope.car_repair_register.component.TokenHandler;
import sk.tope.car_repair_register.dal.domain.RepairLog;
import sk.tope.car_repair_register.dal.repository.RepairLogRepository;
import sk.tope.car_repair_register.dal.specification.RepairLogSpecification;
import sk.tope.car_repair_register.mapper.RepairLogMapper;

@Service
public class RepairLogApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepairLogApiService.class);

    private TokenHandler tokenHandler;

    private RepairLogMapper repairLogMapper;

    private RepairLogRepository repairLogRepository;

    @Autowired
    public void setTokenHandler(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Autowired
    public void setRepairLogMapper(RepairLogMapper repairLogMapper) {
        this.repairLogMapper = repairLogMapper;
    }

    @Autowired
    public void setRepairLogRepository(RepairLogRepository repairLogRepository) {
        this.repairLogRepository = repairLogRepository;
    }

    public Page<RepairLogSo> find(String query, Long vehicleId, Pageable pageable) {
        LOGGER.debug("find({},{},{})", query, vehicleId, pageable);
        Page<RepairLog> result = repairLogRepository.findAll(new RepairLogSpecification(query, vehicleId), pageable);
        LOGGER.debug("find({},{},{})={}", query, vehicleId, pageable, result);
        return result.map(rl -> repairLogMapper.mapToRepairLogSo(rl));
    }

    public RepairLogSo get(Long id) {
        LOGGER.debug("get({})", id);
        RepairLog result = repairLogRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        LOGGER.debug("get({})={}", id, result);
        return repairLogMapper.mapToRepairLogSo(result);
    }

    @Transactional
    public RepairLogSo create(RepairLogCreateSo repairLogCreateSo) {
        LOGGER.debug("create({})", repairLogCreateSo);
        RepairLog repairLog = repairLogMapper.mapToRepairLog(repairLogCreateSo);
        repairLog = repairLogRepository.save(repairLog);
        LOGGER.debug("create({})={}", repairLogCreateSo, repairLog);
        return repairLogMapper.mapToRepairLogSo(repairLog);
    }

    @Transactional
    public RepairLogSo update(Long id, RepairLogUpdateSo repairLogUpdateSo) {
        LOGGER.debug("update({})", repairLogUpdateSo);
        RepairLog repairLog = repairLogRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        repairLogMapper.mapTo(repairLog, repairLogUpdateSo);
        repairLog = repairLogRepository.save(repairLog);
        return repairLogMapper.mapToRepairLogSo(repairLog);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.debug("delete({})", id);
        RepairLog repairLog = repairLogRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        repairLogRepository.delete(repairLog);
    }
}
