package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.State;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.data.BookingStorage;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingRequest;
import ru.practicum.shareit.booking.model.BookingResponse;
import ru.practicum.shareit.exception.BookingNotFoundException;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.ItemUnavailableException;
import ru.practicum.shareit.exception.UnableToChangeBookingStatusException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Qualifier("bookingStorageDatabase")
    private final BookingStorage bookingStorage;
    private final BookingMapper bookingMapper;
    private final UserService userService;
    private final ItemService itemService;
    private final BookingFilter bookingFilter;

    @Override
    public BookingResponse addBooking(BookingRequest bookingRequest, Long userId) {
        User user = getUserIfPresent(userId);
        Long itemId = bookingRequest.getItemId();
        if (!getItemOwner(itemId).equals(userId)) {
            Item item = itemService.getItemById(itemId);
            if (Boolean.TRUE.equals(item.getAvailable())) {
                BookingDto bookingDto = bookingMapper.bookingRequestToBookingDto(bookingRequest, userId);
                BookingDto bookingFromStorage = bookingStorage.add(bookingDto);
                return bookingMapper.bookingDtoToBookingResponse(bookingFromStorage);
            }
            throw new ItemUnavailableException(itemId);
        }
        throw new ForbiddenException();
    }

    @Override
    public BookingResponse updateBooking(Long userId, Long bookingId, boolean isApproved) {
        getUserIfPresent(userId);
        BookingDto bookingDto = getBookingDtoById(bookingId);
        if (isItemOwner(bookingDto, userId)) {
            if(bookingDto.getStatus() != Status.APPROVED) {
                BookingDto updatedBookingDto = bookingDto.toBuilder()
                        .status(getCorrespondingStatus(isApproved))
                        .build();
                BookingDto bookingFromStorage = bookingStorage.update(updatedBookingDto);
                return bookingMapper.bookingDtoToBookingResponse(bookingFromStorage);
            }
             throw new UnableToChangeBookingStatusException();
        }
        throw new ForbiddenException();

    }

    private Status getCorrespondingStatus(boolean isApproved) {
        return isApproved
                ? Status.APPROVED
                : Status.REJECTED;
    }

    @Override
    public BookingResponse getBookingByIdAndUserId(Long bookingId, Long userId) {
        getUserIfPresent(userId);
        BookingDto bookingDto = getBookingDtoById(bookingId);
        if (isBookingOwner(bookingDto, userId) || isItemOwner(bookingDto, userId)) {
            return bookingMapper.bookingDtoToBookingResponse(bookingDto);
        }
        throw new ForbiddenException();
    }

    private BookingDto getBookingDtoById(Long bookingId) {
        return bookingStorage.getById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
    }

    @Override
    public Collection<BookingResponse> getBookingsByUser(Long userId, State state) {
        getUserIfPresent(userId);
        return bookingStorage.getAll().stream()
                .filter(bookingDto -> isBookingOwner(bookingDto, userId))
                .filter(bookingDto -> bookingFilter.isValidBooking(bookingDto, state))
                .map(bookingMapper::bookingDtoToBookingResponse)
                .sorted(Comparator.comparing(BookingResponse::getStart).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<BookingResponse> getBookingsByOwnerId(Long bookerId, State state) {
        getUserIfPresent(bookerId);
        return bookingStorage.getAll().stream()
                .filter(bookingDto -> isItemOwner(bookingDto, bookerId))
                .filter(bookingDto -> bookingFilter.isValidBooking(bookingDto, state))
                .map(bookingMapper::bookingDtoToBookingResponse)
                .sorted(Comparator.comparing(BookingResponse::getStart).reversed())
                .collect(Collectors.toList());
    }

    private boolean isBookingOwner(BookingDto bookingDto, Long bookerId) {
        return Optional.ofNullable(bookingDto.getBooker())
                .map(UserDto::getId)
                .map(bookerId::equals)
                .orElse(false);
    }

    private boolean isItemOwner(BookingDto bookingDto, Long ownerId) {
        return Optional.ofNullable(bookingDto.getItem())
                .map(ItemDto::getOwnerId)
                .map(ownerId::equals)
                .orElse(false);
    }

    private User getUserIfPresent(Long id) {
        return userService.getUserById(id);
    }

    private Item getItemIfPresent(Long id) {
        return itemService.getItemById(id);
    }

    private Long getItemOwner(Long itemId) {
        return itemService.getOwnerForItemByItemId(itemId);
    }
}
