package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class CommentRequest {

    String text;

}
