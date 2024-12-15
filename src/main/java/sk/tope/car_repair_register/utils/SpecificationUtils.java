package sk.tope.car_repair_register.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;

import java.text.Normalizer;

public class SpecificationUtils {

    public static Expression<String> toLowerUnaccent(Path<String> path, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lower(criteriaBuilder.function("unaccent", String.class, path));
    }

    public static String toLowerNormalize(String input) {
        return input == null ? null : Normalizer.normalize(input, Normalizer.Form.NFKD).replaceAll("\\p{M}", "").toLowerCase();
    }
}
