package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookingData {

    Long id;
    Long bookerId;

}
