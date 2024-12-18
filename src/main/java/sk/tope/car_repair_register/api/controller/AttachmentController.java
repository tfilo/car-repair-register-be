package sk.tope.car_repair_register.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sk.tope.car_repair_register.api.service.AttachmentApiService;
import sk.tope.car_repair_register.api.service.so.AttachmentFileSo;
import sk.tope.car_repair_register.api.service.so.AttachmentSo;

import java.io.IOException;

@Tag(name = "attachment")
@RestController
@RequestMapping(value = "/attachment")
public class AttachmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentController.class);

    private final AttachmentApiService attachmentApiService;

    public AttachmentController(AttachmentApiService attachmentApiService) {
        this.attachmentApiService = attachmentApiService;
    }

    @Operation(
            description = "Get attachment file."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "401", description = "unauthorized"),
            @ApiResponse(responseCode = "403", description = "forbidden"),
            @ApiResponse(responseCode = "404", description = "not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @GetMapping(path = "/")
    public ResponseEntity<Resource> downloadAttachment(@RequestParam Long id) {
        LOGGER.debug("getAttachmentFile({})", id);
        AttachmentFileSo attachmentFileSo = attachmentApiService.download(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachmentFileSo.mimeType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachmentFileSo.name() + "\"")
                .body(attachmentFileSo.data());
    }

    @Operation(
            description = "Upload attachment."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created"),
            @ApiResponse(responseCode = "401", description = "unauthorized"),
            @ApiResponse(responseCode = "403", description = "forbidden"),
            @ApiResponse(responseCode = "404", description = "not found"),
            @ApiResponse(responseCode = "413", description = "content too large"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentSo> uploadAttachment(@NotNull @RequestParam Long repairLogId,
                                                         @NotNull @RequestParam MultipartFile multipartFile
    ) throws IOException {
        LOGGER.debug("uploadAttachment({},{})", repairLogId, multipartFile.getOriginalFilename());
        return new ResponseEntity<>(attachmentApiService.upload(repairLogId, multipartFile), HttpStatus.CREATED);
    }

    @Operation(
            description = "Delete attachment."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok"),
            @ApiResponse(responseCode = "401", description = "unauthorized"),
            @ApiResponse(responseCode = "403", description = "forbidden"),
            @ApiResponse(responseCode = "404", description = "not found"),
            @ApiResponse(responseCode = "500", description = "internal server error")
    })
    @DeleteMapping(value = "/{id}")
    public void deleteAttachment(@PathVariable("id") Long id) {
        LOGGER.debug("deleteAttachment({})", id);
        attachmentApiService.delete(id);
    }
}
