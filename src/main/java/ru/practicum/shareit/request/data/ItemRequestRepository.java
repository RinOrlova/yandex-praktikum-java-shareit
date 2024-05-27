package ru.practicum.shareit.request.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequestEntity, Long> {

    List<ItemRequestEntity> getItemRequestEntitiesByRequestor_Id(Long id);
}
