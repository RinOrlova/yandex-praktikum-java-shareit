package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class Item {

    @Nullable
    Long id;
    @NotBlank String name;
    @NotBlank String description;
    @NotNull Boolean available;
    @Nullable
    Long requestId;
    @Nullable
    BookingData lastBooking;
    @Nullable
    BookingData nextBooking;
    @Builder.Default
    List<CommentResponse> comments = new ArrayList<>();
}
