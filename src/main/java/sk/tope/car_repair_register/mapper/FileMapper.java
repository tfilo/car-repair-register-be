package sk.tope.car_repair_register.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.web.multipart.MultipartFile;
import sk.tope.car_repair_register.dal.domain.File;

import java.io.IOException;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        componentModel = "spring")
public interface FileMapper {
    @Mappings({
            @Mapping(target = "data", source = "multipartFile.bytes"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "attachment", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "modified", ignore = true),
            @Mapping(target = "deleted", ignore = true)})
    File mapToEntity(MultipartFile multipartFile) throws IOException;

}
