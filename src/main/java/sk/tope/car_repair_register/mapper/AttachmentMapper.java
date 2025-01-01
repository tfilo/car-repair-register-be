package sk.tope.car_repair_register.mapper;

import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;
import sk.tope.car_repair_register.api.service.so.AttachmentSo;
import sk.tope.car_repair_register.dal.domain.Attachment;

import java.io.IOException;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ACCESSOR_ONLY,
        uses = {FileMapper.class, StringTrimmer.class},
        componentModel = "spring"
)
public interface AttachmentMapper {

    @Mappings({
            @Mapping(target = "file", source = "multipartFile"),
            @Mapping(target = "mimeType", source = "multipartFile.contentType"),
            @Mapping(target = "name", source = "multipartFile.originalFilename"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "repairLog", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "modified", ignore = true),
            @Mapping(target = "deleted", ignore = true),
    })
    Attachment mapFileToEntity(MultipartFile multipartFile) throws IOException;

    AttachmentSo mapToAttachmentSo(Attachment attachment);
}
