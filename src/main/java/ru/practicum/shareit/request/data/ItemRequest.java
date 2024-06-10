package ru.practicum.shareit.request.data;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.data.Item;
import ru.practicum.shareit.user.data.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "requests")
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestor_id", nullable = false)
    private User requestor;

    @OneToMany(mappedBy = "itemRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Collection<Item> itemEntities;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created;
}
