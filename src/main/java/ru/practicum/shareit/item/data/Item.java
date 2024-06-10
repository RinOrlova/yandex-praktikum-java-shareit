package ru.practicum.shareit.item.data;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.data.Booking;
import ru.practicum.shareit.request.data.ItemRequest;
import ru.practicum.shareit.user.data.User;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "items")
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "description", nullable = false, length = 500)
    private String description;
    @Column(name = "is_available", nullable = false)
    private boolean available;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User user;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item")
    private List<Booking> bookings;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "item")
    private Set<Comment> comments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private ItemRequest itemRequest;

}
