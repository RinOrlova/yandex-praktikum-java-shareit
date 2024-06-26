package ru.practicum.shareit.item.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comment, Long>, CustomCommentRefreshRepository {
}
