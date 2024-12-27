package sk.tope.car_repair_register.utils;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortConverter {

    public static Sort convertToSort(String sortString) {
        if (sortString == null || sortString.isEmpty()) {
            return Sort.unsorted();
        }

        String[] parts = sortString.split(",");
        List<Sort.Order> orders = new ArrayList<>();

        for (int i = 0; i < parts.length; i += 2) {
            String property = parts[i].trim();
            String direction = (i + 1 < parts.length) ? parts[i + 1].trim() : "ASC";

            try {
                orders.add(new Sort.Order(Sort.Direction.fromString(direction), property));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid sort direction: " + direction);
            }
        }

        return Sort.by(orders);
    }
}
