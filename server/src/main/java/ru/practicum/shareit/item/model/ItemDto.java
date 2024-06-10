package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class ItemDto {

    @Nullable
    Long id;
    @Nullable
    String name;
    @Nullable
    String description;
    boolean available;
    @Nullable
    Long ownerId;
    @Nullable
    Long requestId;
    @Nullable
    BookingData lastBooking;
    @Nullable
    BookingData nextBooking;
    List<CommentDto> comments;

}