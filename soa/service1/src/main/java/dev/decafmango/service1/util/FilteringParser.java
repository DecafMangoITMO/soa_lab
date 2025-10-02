package dev.decafmango.service1.util;

import dev.decafmango.common.exception.ApplicationExceptionBuilder;
import dev.decafmango.common.model.Organization;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FilteringParser {

    public static Specification<Organization> parseFilteringParam(String filtering) {
        return ((root, query, criteriaBuilder) -> {
            if (filtering == null || filtering.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            String[] filters = filtering.split(",");
            if (filters.length == 0) {
                return criteriaBuilder.conjunction();
            }


            List<Predicate> predicates = new ArrayList<>();
            filter:
            for (String filter : filters) {
                if (filter.split("\\.").length <= 2) {
                    throw ApplicationExceptionBuilder.badRequest("Невалидный формат фильтрации: " + filter);
                }
                for (OrganizationField organizationField : OrganizationField.values()) {
                    String path = organizationField.getPath();
                    String name = organizationField.getName();
                    if (filter.startsWith(path)) {
                        String operator = filter.substring(path.length() + 1, path.length() + filter.substring(path.length() + 1).indexOf(".") + 1);
                        Object value = organizationField.parse(filter.substring(path.length() + operator.length() + 2));

                        predicates.add(switch (operator) {
                                    case "eq" -> criteriaBuilder.equal(root.get(name), value);
                                    case "ne" -> criteriaBuilder.notEqual(root.get(name), value);
                                    case "gt" -> criteriaBuilder.greaterThan(root.get(name), (Comparable) value);
                                    case "gte" -> criteriaBuilder.greaterThanOrEqualTo(root.get(name), (Comparable) value);
                                    case "lt" -> criteriaBuilder.lessThan(root.get(name), (Comparable) value);
                                    case "lte" -> criteriaBuilder.lessThanOrEqualTo(root.get(name), (Comparable) value);
                                    default -> throw ApplicationExceptionBuilder.badRequest("Illegal filtering operator: %s ".formatted(filter));
                                }
                        );
                        continue filter;
                    }
                }
                throw ApplicationExceptionBuilder.badRequest("Illegal filtering: " + filter);
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

}
