package ru.practicum.shareit.item.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.shareit.enums.Status;
import ru.practicum.shareit.booking.data.Booking;
import ru.practicum.shareit.item.data.Item;
import ru.practicum.shareit.item.model.BookingData;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.request.data.ItemRequest;
import ru.practicum.shareit.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = org.mapstruct.MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class, CommentMapper.class})
public interface ItemMapper {
    ru.practicum.shareit.item.model.Item itemDto2Item(ItemDto itemDto);

    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "available", source = "item.available", defaultValue = "false")
    @Mapping(target = "lastBooking", ignore = true)
    @Mapping(target = "nextBooking", ignore = true)
    @Mapping(target = "comments", ignore = true)
    ItemDto item2ItemDto(ru.practicum.shareit.item.model.Item item, Long ownerId);

    default ItemDto mapContext(ru.practicum.shareit.item.model.Item item, @Context Long ownerId) {
        return item2ItemDto(item, ownerId);
    }

    // Mapping from DTO to Entity
    @Mapping(source = "ownerId", target = "user", qualifiedByName = "userIdToUserDto")
    @Mapping(target = "itemRequest", source = "requestId", qualifiedByName = "idToItemRequestEntity")
    @Mapping(target = "bookings", ignore = true)
    Item itemDtoToItemEntity(ItemDto itemDto);

    // Mapping from Entity to DTO
    @Mapping(source = "user", target = "ownerId", qualifiedByName = "userToUserId")
    @Mapping(target = "requestId", source = "itemRequest", qualifiedByName = "itemRequestEntityToId")
    @Mapping(source = "bookings", target = "lastBooking", qualifiedByName = "findLastBooking")
    @Mapping(source = "bookings", target = "nextBooking", qualifiedByName = "findNextBooking")
    ItemDto itemEntityToItemDto(Item item);

    @Named("itemRequestEntityToId")
    default Long itemRequestEntityToId(ItemRequest itemRequest) {
        return itemRequest == null ? null : itemRequest.getId();
    }

    @Named("idToItemRequestEntity")
    default ItemRequest idToItemRequestEntity(Long id) {
        if (id != null) {
            ItemRequest itemRequest = new ItemRequest();
            itemRequest.setId(id);
            return itemRequest;
        }
        return null;
    }

    @Mapping(target = "bookerId", source = "booker", qualifiedByName = "userDtoToUserId")
    BookingData bookingDtoToBookingData(Booking booking);

    @Named("findLastBooking")
    default BookingData findLastBooking(List<Booking> bookings) {
        if (bookings != null) {
            LocalDateTime now = LocalDateTime.now();
            return bookings.stream().filter(booking -> booking.getEnd().isBefore(now) || isCurrentBooking(booking, now)).max(Comparator.comparing(Booking::getEnd)).map(this::bookingDtoToBookingData).orElse(null);
        }
        return null;
    }

    @Named("findNextBooking")
    default BookingData findNextBooking(List<Booking> bookings) {
        if (bookings != null) {
            LocalDateTime now = LocalDateTime.now();
            return bookings.stream()
                    .filter(booking -> booking.getStatus() != Status.REJECTED)
                    .filter(booking -> isFutureBooking(booking, now))
                    .min(Comparator.comparing(Booking::getStart))
                    .map(this::bookingDtoToBookingData)
                    .orElse(null);
        }
        return null;
    }

    private static boolean isCurrentBooking(Booking booking, LocalDateTime now) {
        return booking.getStart().isBefore(now) && booking.getEnd().isAfter(now);
    }

    private static boolean isFutureBooking(Booking booking, LocalDateTime now) {
        return booking.getStart().isAfter(now);
    }

}
