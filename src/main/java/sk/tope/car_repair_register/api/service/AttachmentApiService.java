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
import sk.tope.car_repair_register.api.service.so.*;
import sk.tope.car_repair_register.bundle.ErrorBundle;
import sk.tope.car_repair_register.dal.domain.Attachment;
import sk.tope.car_repair_register.dal.domain.RepairLog;
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

    public AttachmentFileSo download(Long id) {
        LOGGER.debug("download({})", id);
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.ATTACHMENT_NOT_FOUND.name()));

        return new AttachmentFileSo(
                attachment.getName(),
                attachment.getMimeType(),
                new ByteArrayResource(attachment.getFile().getData())
        );
    }

    @Transactional
    public AttachmentSo upload(Long repairLogId, MultipartFile multipartFile) throws IOException {
        LOGGER.debug("upload({},{})", repairLogId, multipartFile.getOriginalFilename());
        RepairLog repairLog = repairLogRepository.findById(repairLogId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.REPAIR_LOG_NOT_FOUND.name()));
        Attachment attachment = attachmentMapper.mapFileToEntity(multipartFile);
        attachment.setRepairLog(repairLog);
        attachment = attachmentRepository.save(attachment);
        return attachmentMapper.mapToAttachmentSo(attachment);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.debug("delete({})", id);
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.ATTACHMENT_NOT_FOUND.name()));
        attachmentRepository.delete(attachment);
    }
}
