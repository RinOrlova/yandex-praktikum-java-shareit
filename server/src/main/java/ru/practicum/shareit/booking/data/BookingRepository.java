package ru.practicum.shareit.booking.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long>, CustomRefreshRepository {

    @Query("SELECT i FROM Booking i WHERE i.booker.id = :id ORDER BY i.start DESC ")
    Page<Booking> findAllForOwner(Long id, Pageable pageable);

    List<Booking> findAllByBooker_Id(Long id);

    @Query("SELECT i FROM Booking i WHERE i.item.user.id = :id ORDER BY i.start DESC ")
    Page<Booking> findAllByItemOwner(Long id, Pageable pageable);

}
