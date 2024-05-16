package ru.practicum.shareit.booking.data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomBookingRepositoryImpl implements CustomBookingRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void refresh(Object o) {
        entityManager.refresh(o);
    }
}
