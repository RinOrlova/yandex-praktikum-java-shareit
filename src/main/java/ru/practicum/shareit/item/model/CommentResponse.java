package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class CommentResponse {

    @Nullable
    Long id;
    @NotBlank String text;
    @NotNull Long itemId;
    @NotNull Long authorId;
    @NotBlank String authorName;
    @NotNull LocalDateTime created;
}
