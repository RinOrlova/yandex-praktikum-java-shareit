package ru.practicum.shareit.request.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    List<ItemRequest> getItemRequestEntitiesByRequestor_Id(Long id);

    @Query("SELECT i FROM ItemRequest i WHERE i.requestor.id <> :id")
    Page<ItemRequest> findAllExcludingRequestorId(@Param("id") Long id, Pageable pageable);

}
