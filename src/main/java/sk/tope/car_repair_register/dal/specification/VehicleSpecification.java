package sk.tope.car_repair_register.dal.specification;

import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import sk.tope.car_repair_register.dal.domain.Customer;
import sk.tope.car_repair_register.dal.domain.Vehicle;
import sk.tope.car_repair_register.utils.SpecificationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record VehicleSpecification(String query, Long customerId) implements Specification<Vehicle> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleSpecification.class);

    @Override
    public Predicate toPredicate(Root<Vehicle> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        criteriaQuery.distinct(true);

        LOGGER.debug("VehicleSpecification({},{})", query, customerId);

        Join<Vehicle, Customer> customerJoin = root.join("customer");

        List<Predicate> predicates = new ArrayList<>();
        if (customerId != null) {
            predicates.add(criteriaBuilder.equal(customerJoin.get("id"), customerId));
        }
        if (StringUtils.hasLength(query)) {
            Arrays.stream(query.split(" ")).map(s -> s.trim()).filter(StringUtils::hasText).forEach(s -> {
                List<Predicate> orPredicates = new ArrayList<>();
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(root.get("registrationPlate"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%", '\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(root.get("vin"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%", '\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(root.get("brand"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%", '\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(root.get("model"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%", '\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(customerJoin.get("name"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%", '\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(customerJoin.get("surname"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%", '\\'));
                predicates.add(criteriaBuilder.or(orPredicates.toArray(Predicate[]::new)));
            });
        }

        return criteriaQuery.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
    }
}
