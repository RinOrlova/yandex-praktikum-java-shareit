package ru.practicum.shareit.booking.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long>, CustomBookingRepository {

}
