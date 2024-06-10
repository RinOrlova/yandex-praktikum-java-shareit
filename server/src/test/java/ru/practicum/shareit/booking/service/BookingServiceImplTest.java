package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    private static final LocalDateTime NOW = LocalDateTime.now();
    @Mock
    private BookingStorage bookingStorage;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private UserService userService;

    @Mock
    private ItemService itemService;

    @Mock
    private BookingFilter bookingFilter;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private User user;
    private Item item;
    private BookingRequest bookingRequest;
    private BookingDto bookingDto;
    private BookingResponse bookingResponse;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Thom Yorke")
                .email("thom.yorke@gmail.com")
                .build();

        item = Item.builder()
                .id(1L)
                .name("Test Item")
                .description("Item description")
                .available(true)
                .build();

        bookingRequest = BookingRequest.builder()
                .start(NOW.plusDays(1L))
                .end(NOW.plusDays(2L))
                .itemId(1L)
                .build();
        UserDto userDto = UserDto.builder().name("Thom Yorke").email("thom.yorke@gmail.com").build();
        bookingDto = BookingDto.builder()
                .id(1L)
                .item(ItemDto.builder().id(1L).ownerId(1L).build())
                .booker(userDto)
                .start(NOW.plusDays(1L))
                .end(NOW.plusDays(2L))
                .status(Status.WAITING)
                .build();

        bookingResponse = BookingResponse.builder()
                .id(1L)
                .item(item)
                .booker(user)
                .start(NOW.plusDays(1L))
                .end(NOW.plusDays(2L))
                .status(Status.WAITING)
                .build();
    }

    @Test
    void addBooking_shouldAddBooking_whenItemAvailable() {
        when(userService.getUserById(2L)).thenReturn(user.toBuilder().id(2L).build());
        when(itemService.getOwnerForItemByItemId(1L)).thenReturn(1L);
        when(itemService.getItemById(1L)).thenReturn(item);
        when(bookingMapper.bookingRequestToBookingDto(bookingRequest, 2L)).thenReturn(bookingDto);
        when(bookingStorage.add(any(BookingDto.class))).thenReturn(bookingDto);
        when(bookingMapper.bookingDtoToBookingResponse(bookingDto)).thenReturn(bookingResponse);

        BookingResponse response = bookingService.addBooking(bookingRequest, 2L);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(bookingDto.getId());
        verify(bookingStorage, times(1)).add(any(BookingDto.class));
    }

    @Test
    void addBooking_shouldThrowItemUnavailableException_whenItemNotAvailable() {
        when(userService.getUserById(1L)).thenReturn(user);
        when(itemService.getOwnerForItemByItemId(1L)).thenReturn(2L);
        when(itemService.getItemById(1L)).thenReturn(item.toBuilder().available(false).build());

        assertThatThrownBy(() -> bookingService.addBooking(bookingRequest, 1L))
                .isInstanceOf(ItemUnavailableException.class);
    }

    @Test
    void updateBooking_shouldUpdateBooking_whenStatusNotApproved() {
        when(userService.getUserById(1L)).thenReturn(user);
        when(bookingStorage.getById(1L)).thenReturn(Optional.of(bookingDto));
        when(bookingStorage.update(any(BookingDto.class))).thenReturn(bookingDto);
        when(bookingMapper.bookingDtoToBookingResponse(any(BookingDto.class))).thenReturn(bookingResponse);

        BookingResponse response = bookingService.updateBooking(1L, 1L, true);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(bookingDto.getId());
        verify(bookingStorage, times(1)).update(any(BookingDto.class));
    }

    @Test
    void updateBooking_shouldThrowUnableToChangeBookingStatusException_whenStatusApproved() {
        bookingDto = bookingDto.toBuilder().status(Status.APPROVED).build();
        when(userService.getUserById(1L)).thenReturn(user);
        when(bookingStorage.getById(1L)).thenReturn(Optional.of(bookingDto));

        assertThatThrownBy(() -> bookingService.updateBooking(1L, 1L, true))
                .isInstanceOf(UnableToChangeBookingStatusException.class);
    }

    @Test
    void getBookingByIdAndUserId_shouldReturnBooking_whenUserIsOwnerOrBooker() {
        when(userService.getUserById(1L)).thenReturn(user);
        when(bookingStorage.getById(1L)).thenReturn(Optional.of(bookingDto));
        when(bookingMapper.bookingDtoToBookingResponse(bookingDto)).thenReturn(bookingResponse);

        BookingResponse response = bookingService.getBookingByIdAndUserId(1L, 1L);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(bookingDto.getId());
    }

    @Test
    void getBookingByIdAndUserId_bookingNotFound() {
        when(userService.getUserById(1L)).thenReturn(user);
        when(bookingStorage.getById(1L)).thenReturn(Optional.empty());
        assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingByIdAndUserId(1L, 1L));
    }

    @Test
    void getBookingByIdAndUserId_shouldThrowForbiddenException_whenUserIsNotOwnerOrBooker() {
        bookingDto = bookingDto.toBuilder().item(ItemDto.builder().id(1L).ownerId(3L).build()).build();
        when(userService.getUserById(1L)).thenReturn(user);
        when(bookingStorage.getById(1L)).thenReturn(Optional.of(bookingDto));

        assertThatThrownBy(() -> bookingService.getBookingByIdAndUserId(1L, 1L))
                .isInstanceOf(ForbiddenException.class);
    }

    @Test
    void getBookingsByUser_shouldReturnBookings_whenStateValid() {
        when(userService.getUserById(1L)).thenReturn(user);
        when(bookingStorage.getAllForBookingOwner(1L, 0, 10)).thenReturn(Collections.singletonList(bookingDto));
        when(bookingFilter.isValidBooking(any(BookingDto.class), any(State.class))).thenReturn(true);
        when(bookingMapper.bookingDtoToBookingResponse(any(BookingDto.class))).thenReturn(bookingResponse);

        Collection<BookingResponse> responses = bookingService.getBookingsByUser(1L, State.ALL, 0, 10);

        assertThat(responses).hasSize(1);
        assertThat(responses.iterator().next().getId()).isEqualTo(bookingDto.getId());
    }

    @Test
    void getBookingsByOwnerId_shouldReturnBookings_whenStateValid() {
        when(userService.getUserById(1L)).thenReturn(user);
        when(bookingStorage.getAllByItemOwner(1L, 0, 10)).thenReturn(Collections.singletonList(bookingDto));
        when(bookingFilter.isValidBooking(any(BookingDto.class), any(State.class))).thenReturn(true);
        when(bookingMapper.bookingDtoToBookingResponse(any(BookingDto.class))).thenReturn(bookingResponse);

        Collection<BookingResponse> responses = bookingService.getBookingsByOwnerId(1L, State.ALL, 0, 10);

        assertThat(responses).hasSize(1);
        assertThat(responses.iterator().next().getId()).isEqualTo(bookingDto.getId());
    }
}
