package ru.practicum.shareit.validation;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.PaginationSizeException;

@Component
public class PaginationSizeValidator {

    public int validatePaginationSize(int from, int size, long totalAmountOfEntries) {
        if (from >= totalAmountOfEntries) {
            throw new PaginationSizeException(from, size);
        }
        if (from + size > totalAmountOfEntries) {
            return (int) totalAmountOfEntries - from;
        }
        return size;
    }

}
