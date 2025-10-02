package dev.decafmango.service1.util;

import dev.decafmango.common.exception.ApplicationExceptionBuilder;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortingParser {

    public static Sort parseSortingParam(String sorting) {
        if (sorting == null || sorting.isEmpty()) {
            return Sort.unsorted();
        }

        String[] sorts = sorting.split(",");
        if (sorts.length == 0) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = new ArrayList<>();
        sort:
        for (String sort : sorts) {
            if (sort.split("\\.").length == 1) {
                throw ApplicationExceptionBuilder.badRequest("Невалидный формат сортировки: " + sort);
            }
            for (OrganizationField organizationField : OrganizationField.values()) {
                String path = organizationField.getPath();
                String name = organizationField.getName();
                if (sort.startsWith(path)) {
                    String direction = sort.substring(path.length() + 1);
                    Sort.Order order;
                    if ("asc".equals(direction)) {
                        order = Sort.by(Sort.Direction.ASC, name).getOrderFor(name);
                    } else if ("desc".equals(direction)) {
                        order = Sort.by(Sort.Direction.DESC, name).getOrderFor(name);
                    } else {
                        throw ApplicationExceptionBuilder.badRequest("Неверный формат направления сортировки: " + direction);
                    }
                    orders.add(order);
                    continue sort;
                }
            }
            throw ApplicationExceptionBuilder.badRequest("Неверный формат сортировки: " + sort);
        }

        return Sort.by(orders);
    }

}