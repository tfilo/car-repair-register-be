package sk.tope.car_repair_register.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sk.tope.car_repair_register.api.service.so.VehicleCreateSo;
import sk.tope.car_repair_register.api.service.so.VehicleSo;
import sk.tope.car_repair_register.api.service.so.VehicleUpdateSo;
import sk.tope.car_repair_register.bundle.ErrorBundle;
import sk.tope.car_repair_register.dal.domain.Customer;
import sk.tope.car_repair_register.dal.domain.Vehicle;
import sk.tope.car_repair_register.dal.repository.CustomerRepository;
import sk.tope.car_repair_register.dal.repository.VehicleRepository;
import sk.tope.car_repair_register.dal.specification.VehicleSpecification;
import sk.tope.car_repair_register.mapper.VehicleMapper;

@Service
public class VehicleApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleApiService.class);

    private VehicleMapper vehicleMapper;

    private VehicleRepository vehicleRepository;

    private CustomerRepository customerRepository;

    @Autowired
    public void setVehicleMapper(VehicleMapper vehicleMapper) {
        this.vehicleMapper = vehicleMapper;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Page<VehicleSo> find(String query, Long customerId, Pageable pageable) {
        LOGGER.debug("find({},{},{})", query, customerId, pageable);
        Page<Vehicle> result = vehicleRepository.findAll(new VehicleSpecification(query, customerId), pageable);
        LOGGER.debug("find({},{},{})={}", query, customerId, pageable, result);
        return result.map(v -> vehicleMapper.mapToVehicleSo(v));
    }

    public VehicleSo get(Long id) {
        LOGGER.debug("get({})", id);
        Vehicle result = vehicleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.VEHICLE_NOT_FOUND.name()));
        LOGGER.debug("get({})={}", id, result);
        return vehicleMapper.mapToVehicleSo(result);
    }

    @Transactional
    public VehicleSo create(VehicleCreateSo vehicleCreateSo) {
        LOGGER.debug("create({})", vehicleCreateSo);
        Vehicle vehicle = vehicleMapper.mapToVehicle(vehicleCreateSo);
        Customer customer = customerRepository.findById(vehicleCreateSo.customerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.CUSTOMER_NOT_FOUND.name()));
        vehicle.setCustomer(customer);
        vehicle = vehicleRepository.save(vehicle);
        LOGGER.debug("create({})={}", vehicleCreateSo, vehicle);
        return vehicleMapper.mapToVehicleSo(vehicle);
    }

    @Transactional
    public VehicleSo update(Long id, VehicleUpdateSo vehicleUpdateSo) {
        LOGGER.debug("update({})", vehicleUpdateSo);
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.VEHICLE_NOT_FOUND.name()));
        vehicleMapper.mapTo(vehicle, vehicleUpdateSo);
        if (!vehicle.getCustomer().getId().equals(vehicleUpdateSo.customerId())) {
            Customer customer = customerRepository.findById(vehicleUpdateSo.customerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.CUSTOMER_NOT_FOUND.name()));
            vehicle.setCustomer(customer);
        }
        vehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.mapToVehicleSo(vehicle);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.debug("delete({})", id);
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorBundle.VEHICLE_NOT_FOUND.name()));
        vehicleRepository.delete(vehicle);
    }
}
