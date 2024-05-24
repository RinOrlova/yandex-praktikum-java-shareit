package ru.practicum.shareit.request.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRequestRepository extends JpaRepository<ItemRequestEntity, Long> {
}
