package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class CommentRequest {
    @JsonProperty("text")
    @NotBlank String text;

}
