package ru.practicum.shareit.booking.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long>, CustomRefreshRepository {

    @Query("SELECT i FROM BookingEntity i WHERE i.booker.id = :id ORDER BY i.start DESC ")
    Page<BookingEntity> findAllForOwner(Long id, Pageable pageable);

    List<BookingEntity> findAllByBooker_Id(Long id);

    @Query("SELECT i FROM BookingEntity i WHERE i.item.userEntity.id = :id ORDER BY i.start DESC ")
    Page<BookingEntity> findAllByItemOwner(Long id, Pageable pageable);

}
