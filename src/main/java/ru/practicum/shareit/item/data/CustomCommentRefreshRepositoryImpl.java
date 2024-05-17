package ru.practicum.shareit.item.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomCommentRefreshRepositoryImpl implements CustomCommentRefreshRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void refresh(Object o) {
        entityManager.refresh(o);
    }

}
