package sk.tope.car_repair_register.api.service.so;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

public class RepairLogPagedModel extends PagedModel<RepairLogSo> {

    public RepairLogPagedModel(Page<RepairLogSo> page) {
        super(page);
    }
}
