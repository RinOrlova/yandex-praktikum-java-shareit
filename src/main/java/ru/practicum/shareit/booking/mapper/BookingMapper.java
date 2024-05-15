package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.shareit.booking.data.BookingEntity;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.mapper.UserMapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ItemMapper.class})
public interface BookingMapper {

    @Mapping(target = "itemId", source = "item.id")
    Booking bookingDto2Booking(BookingDto bookingDto);

    BookingDto booking2BookingDto(Booking booking);

    BookingDto bookingEntity2BookingDto(BookingEntity bookingEntity);

    BookingEntity bookingDto2BookingEntity(BookingDto bookingDto);

    @Named("bookingToBookingId")
    default Long bookingToBookingId(BookingEntity bookingEntity) {
        return bookingEntity == null ? null : bookingEntity.getId();
    }

}