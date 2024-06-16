package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class CommentDto {
    @Nullable
    Long id;
    String text;
    Long itemId;
    Long authorId;
    @Nullable
    String authorName;
    @Builder.Default
    LocalDateTime created = LocalDateTime.now();
}
