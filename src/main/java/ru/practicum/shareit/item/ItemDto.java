package ru.practicum.shareit.item;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.UserDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "items")
@Data
@Builder(toBuilder = true)
public class ItemDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Nullable
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    @NotBlank private String name;
    @Column(name = "description", nullable = false, length = 500)
    @NotBlank private String description;
    @Column(name = "is_available", nullable = false)
    boolean available;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @NonNull private UserDto userDto;
    @JoinColumn(name = "request_id")
    @Nullable
    Integer requestId;

}