package sk.tope.car_repair_register.dal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import sk.tope.car_repair_register.dal.domain.Vehicle;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Page<Vehicle> findAll(Specification<Vehicle> specification, Pageable pageable);

    Optional<Vehicle> findById(Long id);
}
