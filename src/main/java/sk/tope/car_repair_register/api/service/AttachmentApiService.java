package sk.tope.car_repair_register.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sk.tope.car_repair_register.api.service.so.AttachmentFileSo;
import sk.tope.car_repair_register.api.service.so.AttachmentSo;
import sk.tope.car_repair_register.bundle.ErrorBundle;
import sk.tope.car_repair_register.dal.domain.Attachment;
import sk.tope.car_repair_register.dal.domain.RepairLog;
import sk.tope.car_repair_register.dal.domain.Vehicle;
import sk.tope.car_repair_register.dal.repository.AttachmentRepository;
import sk.tope.car_repair_register.dal.repository.RepairLogRepository;
import sk.tope.car_repair_register.mapper.AttachmentMapper;

import java.io.IOException;

@Service
public class AttachmentApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentApiService.class);

    private AttachmentMapper attachmentMapper;

    private AttachmentRepository attachmentRepository;

    private RepairLogRepository repairLogRepository;

    private FileStorageService fileStorageService;

    @Autowired
    public void setAttachmentMapper(AttachmentMapper attachmentMapper) {
        this.attachmentMapper = attachmentMapper;
    }

    @Autowired
    public void setAttachmentRepository(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Autowired
    public void setRepairLogRepository(RepairLogRepository repairLogRepository) {
        this.repairLogRepository = repairLogRepository;
    }

    @Autowired
    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Transactional(readOnly = true)
    public AttachmentFileSo download(Long id) {
        LOGGER.debug("download({})", id);
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ErrorBundle.ATTACHMENT_NOT_FOUND.name()));

        Vehicle vehicle = attachment.getRepairLog().getVehicle();
        Long vehicleId = vehicle.getId();
        Long customerId = vehicle.getCustomer().getId();

        byte[] data = null;
        try {
            data = fileStorageService.readFile(attachment.getFileName(), vehicleId, customerId);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    ErrorBundle.ATTACHMENT_NOT_FOUND.name());
        }

        return new AttachmentFileSo(attachment.getName(), attachment.getMimeType(),
                new ByteArrayResource(data));
    }

    @Transactional
    public AttachmentSo upload(Long repairLogId, MultipartFile multipartFile) throws IOException {
        LOGGER.debug("upload({},{})", repairLogId, multipartFile.getOriginalFilename());
        RepairLog repairLog = repairLogRepository.findById(repairLogId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ErrorBundle.REPAIR_LOG_NOT_FOUND.name()));

        Vehicle vehicle = repairLog.getVehicle();
        Long vehicleId = vehicle.getId();
        Long customerId = vehicle.getCustomer().getId();

        String fileName =
                fileStorageService.saveFile(multipartFile.getBytes(), vehicleId, customerId);
        Attachment attachment = attachmentMapper.mapFileToEntity(multipartFile, fileName);
        attachment.setRepairLog(repairLog);
        attachment = attachmentRepository.saveAndFlush(attachment);
        return attachmentMapper.mapToAttachmentSo(attachment);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.debug("delete({})", id);
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        ErrorBundle.ATTACHMENT_NOT_FOUND.name()));

        attachmentRepository.delete(attachment);
    }
}
