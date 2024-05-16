package ru.practicum.shareit.item.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.shareit.booking.data.BookingEntity;
import ru.practicum.shareit.item.data.ItemEntity;
import ru.practicum.shareit.item.model.BookingData;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.data.UserEntity;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CommentMapper.class})
public interface ItemMapper {
    Item itemDto2Item(ItemDto itemDto);

    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "available", source = "item.available", defaultValue = "false")
    @Mapping(target = "lastBooking", ignore = true)
    @Mapping(target = "nextBooking", ignore = true)
    @Mapping(target = "comments", ignore = true)
    ItemDto item2ItemDto(Item item, Long ownerId);

    default ItemDto mapContext(Item item, @Context Long ownerId) {
        return item2ItemDto(item, ownerId);
    }

    // Mapping from DTO to Entity
    @Mapping(source = "ownerId", target = "userEntity", qualifiedByName = "userIdToUserDto")
    @Mapping(target = "bookings", ignore = true)
    ItemEntity itemDtoToItemEntity(ItemDto itemDto);

    // Mapping from Entity to DTO
    @Mapping(source = "userEntity", target = "ownerId", qualifiedByName = "userToUserId")
    @Mapping(target = "requestId", ignore = true)
    @Mapping(source = "bookings", target = "lastBooking", qualifiedByName = "findLastBooking")
    @Mapping(source = "bookings", target = "nextBooking", qualifiedByName = "findNextBooking")
    ItemDto itemEntityToItemDto(ItemEntity itemEntity);



    @Named("requestToRequestId")
    default Long requestToRequestId(ItemRequest itemRequest) {
        return itemRequest == null ? null : itemRequest.getId();
    }

    @Named("itemToItemId")
    default Long itemToItemId(ItemEntity item) {
        return item == null ? null : item.getId();
    }

    @Mapping(target = "bookerId", source = "booker", qualifiedByName = "userDtoToUserId")
    BookingData bookingDtoToBookingData(BookingEntity bookingEntity);

    @Named("findLastBooking")
    default BookingData findLastBooking(List<BookingEntity> bookings) {
        if (bookings != null) {
            LocalDateTime now = LocalDateTime.now();
            return bookings.stream()
                    .filter(booking -> booking.getEnd().isBefore(now))
                    .max(Comparator.comparing(BookingEntity::getEnd))
                    .map(this::bookingDtoToBookingData)
                    .orElse(null);
        }
        return null;
    }

    @Named("findNextBooking")
    default BookingData findNextBooking(List<BookingEntity> bookings) {
        if (bookings != null) {
            LocalDateTime now = LocalDateTime.now();
            return bookings.stream()
                    .filter(booking -> booking.getStart().isAfter(now))
                    .min(Comparator.comparing(BookingEntity::getStart))
                    .map(this::bookingDtoToBookingData)
                    .orElse(null);
        }
        return null;
    }

}
