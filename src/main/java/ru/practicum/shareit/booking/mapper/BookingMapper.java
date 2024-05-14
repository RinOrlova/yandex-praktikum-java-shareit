package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.shareit.booking.data.BookingEntity;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.item.data.ItemEntity;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.data.UserEntity;
import ru.practicum.shareit.user.mapper.UserMapper;

@Mapper(componentModel = "spring", uses ={UserMapper.class, ItemMapper.class})
public interface BookingMapper {

    Booking bookingDto2Booking(BookingDto bookingDto);

    @Mapping(target = "bookerId", source = "bookerId")
    BookingDto booking2BookingDto(Booking booking, Long bookerId);

    default BookingDto mapContext(Booking item,
                                  @Context  Long bookerId) {
        return booking2BookingDto(item, bookerId);
    }

    @Mapping(source = "item", target = "itemId", qualifiedByName = "itemToItemId")
    @Mapping(source = "booker", target = "bookerId", qualifiedByName = "userToUserId")
    BookingDto bookingEntity2BookingDto(BookingEntity bookingEntity);

    // Mapping from DTO to Entity
    @Mapping(source = "itemId", target = "item", qualifiedByName = "itemIdToItemDto")
    @Mapping(source = "bookerId", target = "booker", qualifiedByName = "bookerIdToBooker")
    BookingEntity bookingDto2BookingEntity(BookingDto bookingDto);

    // Helper method to convert userId to ItemEntity
    @Named("itemIdToItemDto")
    default ItemEntity itemIdToItemDto(Long itemId) {
        if (itemId == null) {
            return null;
        }
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemId);
        return itemEntity;
    }

    // Helper method to convert userId to UserEntity
    @Named("bookerIdToBooker")
    default UserEntity bookerIdToBooker(Long bookerId) {
        if (bookerId == null) {
            return null;
        }
        UserEntity user = new UserEntity();
        user.setId(bookerId);
        return user;
    }


    @Named("bookingToBookingId")
    default Long bookingToBookingId(BookingEntity bookingEntity) {
        return bookingEntity == null ? null : bookingEntity.getId();
    }

}
