package ru.practicum.shareit.request.data;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.data.ItemEntity;
import ru.practicum.shareit.user.data.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "requests")
public class ItemRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestor_id", nullable = false)
    private UserEntity requestor;

    @OneToMany(mappedBy = "itemRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Collection<ItemEntity> itemEntities;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created;
}
