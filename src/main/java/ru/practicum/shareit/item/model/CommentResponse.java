package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
public class CommentResponse {

    @Nullable Long id;
    @NotBlank String text;
    @NotNull Long itemId;
    @NotNull Long authorId;
    @NotNull LocalDate created;
}
