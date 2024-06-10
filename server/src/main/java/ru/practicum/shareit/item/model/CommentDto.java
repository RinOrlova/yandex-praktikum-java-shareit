package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class CommentDto {
    @Nullable
    Long id;
    @NotBlank String text;
    @NotNull Long itemId;
    @NotNull Long authorId;
    @Nullable
    String authorName;
    @NotNull
    @Builder.Default
    LocalDateTime created = LocalDateTime.now();
}
