package sk.tope.car_repair_register.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        componentModel = "spring")
public class StringTrimmer {
    public String trimString(String value) {
        return value.trim();
    }
}
