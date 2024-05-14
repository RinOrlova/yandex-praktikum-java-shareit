package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import ru.practicum.shareit.booking.data.BookingEntity;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking BookingDto2Booking(BookingDto bookingDto);

    BookingDto Booking2BookingDto(Booking booking);

    BookingDto BookingEntity2BookingDto(BookingEntity bookingEntity);

    BookingEntity BookingDto2BookingEntity(BookingDto bookingDto);

    @Named("bookingToBookingId")
    default Long bookingToBookingId(BookingEntity bookingEntity) {
        return bookingEntity == null ? null : bookingEntity.getId();
    }

}
