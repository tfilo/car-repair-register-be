package sk.tope.car_repair_register.dal.specification;

import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import sk.tope.car_repair_register.dal.domain.Customer;
import sk.tope.car_repair_register.utils.SpecificationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record CustomerSpecification(String query, String login) implements Specification<Customer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSpecification.class);

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        criteriaQuery.distinct(true);

        LOGGER.debug("CustomerSpecification({})", query);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.isNull(root.get("deleted")));
        predicates.add(criteriaBuilder.equal(root.get("entityOwner"), login));
        if (StringUtils.hasLength(query)) {
            Arrays.stream(query.split(" ")).map(s -> s.trim()).filter(StringUtils::hasText).forEach(s -> {
                List<Predicate> orPredicates = new ArrayList<>();
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(root.get("name"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%",'\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(root.get("surname"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%",'\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(root.get("mobile"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%",'\\'));
                orPredicates.add(criteriaBuilder.like(SpecificationUtils.toLowerUnaccent(root.get("email"), criteriaBuilder), "%" + SpecificationUtils.toLowerNormalize(s) + "%",'\\'));
                predicates.add(criteriaBuilder.or(orPredicates.toArray(Predicate[]::new)));
            });
        }

        return criteriaQuery.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
    }


}
