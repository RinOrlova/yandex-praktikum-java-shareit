package ru.practicum.shareit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.booking.model.BookingRequest;
import ru.practicum.shareit.booking.model.BookingResponse;
import ru.practicum.shareit.item.model.CommentRequest;
import ru.practicum.shareit.item.model.CommentResponse;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ShareItTests {

    private static final LocalDateTime NOW = LocalDateTime.now();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void testUserOperations() throws Exception {
        User user = User.builder()
                .name("Thom Yorke")
                .email("thom.yorke@gmail.com")
                .build();

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Thom Yorke"))
                .andExpect(jsonPath("$.email").value("thom.yorke@gmail.com"));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Thom Yorke"))
                .andExpect(jsonPath("$.email").value("thom.yorke@gmail.com"));


        User updatedUser = User.builder()
                .name("Thom Yorke Updated")
                .email("thom.yorke@gmail.com")
                .build();

        mockMvc.perform(patch("/users/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Thom Yorke Updated"));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void testItemOperations() throws Exception {
        Item item = Item.builder()
                .name("Test Item")
                .description("Item description")
                .available(true)
                .build();
        // create new item
        mockMvc.perform(post("/items")
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Test Item"))
                .andExpect(jsonPath("$.description").value("Item description"))
                .andExpect(jsonPath("$.available").value("true"));
        // get item by id
        mockMvc.perform(get("/items/1")
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Test Item"))
                .andExpect(jsonPath("$.description").value("Item description"))
                .andExpect(jsonPath("$.available").value("true"));
        // get all items
        String getAllItemsResult = mockMvc.perform(get("/items")
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Collection<Item> items = objectMapper.readValue(getAllItemsResult, new TypeReference<>() {
        });
        assertEquals(1, items.size());
        Item resultItem = items.iterator().next();
        assertEquals("Test Item", resultItem.getName());
        assertEquals("Item description", resultItem.getDescription());
        assertEquals(true, resultItem.getAvailable());
        // search items
        String searchItemsResponse = mockMvc.perform(get("/items/search?text=Item")
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Collection<Item> searchItemsResult = objectMapper.readValue(searchItemsResponse, new TypeReference<>() {
        });
        assertEquals(1, items.size());
        Item searchItem = searchItemsResult.iterator().next();
        assertEquals("Test Item", searchItem.getName());
        assertEquals("Item description", searchItem.getDescription());
        assertEquals(true, searchItem.getAvailable());
    }

    @Test
    @Order(3)
    void testBookingOperations() throws Exception {
        BookingRequest bookingRequest = BookingRequest.builder()
                .start(NOW.plusSeconds(10))
                .end(NOW.plusSeconds(12))
                .itemId(1L)
                .build();
        // create booking requestor
        User requestor = User.builder()
                .name("Requestor")
                .email("requestor@gmail.com")
                .build();

        String requestorJson = mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Requestor"))
                .andExpect(jsonPath("$.email").value("requestor@gmail.com"))
                .andReturn().getResponse().getContentAsString();
        User createdRequestor = objectMapper.readValue(requestorJson, User.class);

        // create booking
        String bookingResponseJson = mockMvc.perform(post("/bookings")
                        .header(ApiPathConstants.X_SHARER_USER_ID, createdRequestor.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        BookingResponse bookingResponse = objectMapper.readValue(bookingResponseJson, BookingResponse.class);
        assertEquals("Requestor", bookingResponse.getBooker().getName());
        assertEquals("requestor@gmail.com", bookingResponse.getBooker().getEmail());
        assertEquals("Test Item", bookingResponse.getItem().getName());
        assertEquals("Item description", bookingResponse.getItem().getDescription());
        // get booking by id
        String bookingResponseByIdJson = mockMvc.perform(get("/bookings/1")
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        BookingResponse bookingResponseById = objectMapper.readValue(bookingResponseByIdJson, BookingResponse.class);
        assertEquals("Requestor", bookingResponseById.getBooker().getName());
        assertEquals("requestor@gmail.com", bookingResponseById.getBooker().getEmail());
        assertEquals("Test Item", bookingResponseById.getItem().getName());
        assertEquals("Item description", bookingResponseById.getItem().getDescription());
        // update booking status
        String updatedBookingJson = mockMvc.perform(patch("/bookings/1?approved=true")
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        BookingResponse updatedBooking = objectMapper.readValue(updatedBookingJson, BookingResponse.class);
        assertEquals(Status.APPROVED, updatedBooking.getStatus());
        // get booking by booking creator
        String bookingResponseByUserJson = mockMvc.perform(get("/bookings")
                        .header(ApiPathConstants.X_SHARER_USER_ID, 2L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Collection<BookingResponse> bookingResponseByUser = objectMapper.readValue(bookingResponseByUserJson, new TypeReference<>() {
        });
        assertEquals(1, bookingResponseByUser.size());
        // get booking by item owner
        String bookingResponseByItemOwnerJson = mockMvc.perform(get("/bookings/owner")
                        .header(ApiPathConstants.X_SHARER_USER_ID, 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Collection<BookingResponse> bookingResponseByItemOwner = objectMapper.readValue(bookingResponseByItemOwnerJson, new TypeReference<>() {
        });
        assertEquals(1, bookingResponseByItemOwner.size());
    }

    @Test
    @Order(4)
    void addCommentOperation() throws Exception {
        Thread.sleep(15000); // wait until booking is in the past
        CommentRequest commentRequest = CommentRequest.builder()
                .text("comment text")
                .build();

        String commentResonseJson = mockMvc.perform(post("/items/1/comment")
                        .header(ApiPathConstants.X_SHARER_USER_ID, 2L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(commentRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CommentResponse commentResponse = objectMapper.readValue(commentResonseJson, CommentResponse.class);
        assertEquals(2L, commentResponse.getAuthorId());
        assertEquals("Requestor", commentResponse.getAuthorName());
        assertEquals("comment text", commentResponse.getText());

    }
}
