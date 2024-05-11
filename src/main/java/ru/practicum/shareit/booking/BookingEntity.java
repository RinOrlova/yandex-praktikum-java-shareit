package ru.practicum.shareit.booking;

import lombok.*;
import org.springframework.lang.Nullable;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "bookings")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "start_date", nullable = false)
    private LocalDateTime start;
    @Column(name = "end_date", nullable = false)
    private LocalDateTime end;
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    @Column(name = "booker_id", nullable = false)
    private Long bookerId;
    @Column(name = "status", nullable = false)
    private Status status;
}
