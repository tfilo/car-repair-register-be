package sk.tope.car_repair_register.dal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import sk.tope.car_repair_register.dal.domain.RepairLog;

import java.util.Optional;

public interface RepairLogRepository extends JpaRepository<RepairLog, Long> {

    Page<RepairLog> findAll(Specification<RepairLog> specification, Pageable pageable);

    Optional<RepairLog> findById(Long id);
}
