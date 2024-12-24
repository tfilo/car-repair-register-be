package sk.tope.car_repair_register.api.service.so;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

public class VehiclePagedModel extends PagedModel<VehicleSo> {

    public VehiclePagedModel(Page<VehicleSo> page) {
        super(page);
    }
}
