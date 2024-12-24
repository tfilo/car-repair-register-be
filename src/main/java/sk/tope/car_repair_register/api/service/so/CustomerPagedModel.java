package sk.tope.car_repair_register.api.service.so;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

public class CustomerPagedModel extends PagedModel<CustomerSo> {

    public CustomerPagedModel(Page<CustomerSo> page) {
        super(page);
    }
}
