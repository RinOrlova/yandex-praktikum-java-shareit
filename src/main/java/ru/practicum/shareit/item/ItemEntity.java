package ru.practicum.shareit.item;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.data.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "items")
@Getter
@Setter
public class ItemEntity {
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
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity userDto;
/*    @JoinColumn(name = "request_id")
    private Integer requestId;*/


}
