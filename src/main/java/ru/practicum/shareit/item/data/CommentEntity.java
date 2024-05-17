package ru.practicum.shareit.item.data;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.data.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "text", nullable = false, length = 1000)
    String text;
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    ItemEntity itemEntity;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    UserEntity userEntity;
    @Column(name = "created_at", nullable = false)
    LocalDateTime created;
}
