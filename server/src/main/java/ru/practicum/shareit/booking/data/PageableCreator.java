package ru.practicum.shareit.booking.data;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@UtilityClass
public class PageableCreator {
    public static Pageable getPageable(int from, int size, double totalBookingByOwner) {
        int maxPageValue = (int) Math.ceil(totalBookingByOwner / size) - 1;
        if (from > maxPageValue) {
            from = maxPageValue;
        }
        return PageRequest.of(from, size);
    }
}