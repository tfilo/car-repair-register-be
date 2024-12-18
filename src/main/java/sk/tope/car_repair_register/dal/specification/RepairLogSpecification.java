package sk.tope.car_repair_register.dal.specification;

import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import sk.tope.car_repair_register.dal.domain.Customer;
import sk.tope.car_repair_register.dal.domain.RepairLog;
import sk.tope.car_repair_register.dal.domain.Vehicle;
import sk.tope.car_repair_register.utils.SpecificationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record RepairLogSpecification(String query, Long vehicleId) implements Specification<RepairLog> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepairLogSpecification.class);

    @Override
    public Predicate toPredicate(Root<RepairLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        criteriaQuery.distinct(true);

        LOGGER.debug("RepairLogSpecification({},{})", query, vehicleId);

        Join<RepairLog, Vehicle> vehicleJoin = root.join("vehicle");
        Join<Vehicle, Customer> customerJoin = vehicleJoin.join("customer");

        List<Predicate> predicates = new ArrayList<>();
        if (vehicleId != null) {
            predicates.add(criteriaBuilder.equal(vehicleJoin.get("id"), vehicleId));
        }
        if (StringUtils.hasLength(query)) {
            Arrays.stream(query.split(" ")).map(s -> s.trim()).filter(StringUtils::hasText).forEach(s -> {
                List<Predicate> orPredicates = new ArrayList<>();
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(root.get("content"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%", '\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(vehicleJoin.get("registrationPlate"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%", '\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(vehicleJoin.get("vin"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%", '\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(vehicleJoin.get("brand"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%", '\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(vehicleJoin.get("model"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%", '\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(customerJoin.get("name"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%", '\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(customerJoin.get("surname"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%", '\\'));
                predicates.add(criteriaBuilder.or(orPredicates.toArray(Predicate[]::new)));
            });
        }

        return criteriaQuery.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
    }
}
