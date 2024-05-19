package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.shareit.booking.data.BookingEntity;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingRequest;
import ru.practicum.shareit.booking.model.BookingResponse;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.UserDto;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class, ItemMapper.class})
public interface BookingMapper {

    @Mapping(target = "booker", source = "bookerId", qualifiedByName = "bookerIdToUserDto")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "item", source = "bookingRequest.itemId", qualifiedByName = "itemIdToItemDto")
    BookingDto bookingRequestToBookingDto(BookingRequest bookingRequest, Long bookerId);

    default BookingDto mapContext(BookingRequest item,
                                  @Context Long bookerId) {
        return bookingRequestToBookingDto(item, bookerId);
    }

    BookingResponse bookingDtoToBookingResponse(BookingDto bookingDto);

    @Named("itemIdToItemDto")
    default ItemDto itemIdToItemDto(Long itemId) {
        if (itemId == null) {
            return null;
        }
        return ItemDto.builder()
                .id(itemId)
                .build();
    }

    @Named("bookerIdToUserDto")
    default UserDto bookerIdToUserDto(Long userId) {
        if (userId == null) {
            return null;
        }
        return UserDto.builder()
                .id(userId)
                .build();
    }

    BookingDto bookingEntity2BookingDto(BookingEntity bookingEntity);

    BookingEntity bookingDto2BookingEntity(BookingDto bookingDto);

    @Named("bookingToBookingId")
    default Long bookingToBookingId(BookingEntity bookingEntity) {
        return bookingEntity == null ? null : bookingEntity.getId();
    }

}