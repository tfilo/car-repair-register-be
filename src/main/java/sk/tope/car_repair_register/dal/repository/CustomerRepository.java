package sk.tope.car_repair_register.dal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import sk.tope.car_repair_register.dal.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findAll(Specification<Customer> specification, Pageable pageable);
}
