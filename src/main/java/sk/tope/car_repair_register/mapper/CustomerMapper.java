package sk.tope.car_repair_register.mapper;

import org.mapstruct.*;
import sk.tope.car_repair_register.api.service.so.CustomerCreateSo;
import sk.tope.car_repair_register.api.service.so.CustomerSo;
import sk.tope.car_repair_register.api.service.so.CustomerUpdateSo;
import sk.tope.car_repair_register.dal.domain.Customer;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        componentModel = "spring")
public interface CustomerMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "modified", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            //@Mapping(target = "vehicles", ignore = true)
    })
    Customer mapToCustomer(CustomerCreateSo customerCreateSo);

    CustomerSo mapToCustomerSo(Customer customer);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "modified", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            //@Mapping(target = "vehicles", ignore = true)
    })
    void mapTo(@MappingTarget Customer customer, CustomerUpdateSo customerUpdateSo);
}
