package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ApplicationExceptionHandlerTest.TestController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ApplicationExceptionHandler.class)
})
@ContextConfiguration(classes = {ApplicationExceptionHandler.class, ApplicationExceptionHandlerTest.TestController.class})
class ApplicationExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @RestController
    @RequestMapping("/test")
    public static class TestController {
        @GetMapping("/invalidData")
        public void throwInvalidDataInRequestException() {
            throw new InvalidDataInRequestException("Invalid data provided");
        }

        @GetMapping("/itemUnavailable")
        public void throwItemUnavailableException() {
            throw new ItemUnavailableException(1L);
        }

        @GetMapping("/unableToChangeBookingStatus")
        public void throwUnableToChangeBookingStatusException() {
            throw new UnableToChangeBookingStatusException();
        }

        @GetMapping("/addCommentForbidden")
        public void throwAddCommentForbiddenException() {
            throw new AddCommentForbiddenException();
        }

        @GetMapping("/constraintViolation")
        public void throwConstraintViolationException() {
            throw new ConstraintViolationException("Invalid request", new HashSet<>());
        }

        @GetMapping("/notFound")
        public void throwNotFoundException() {
            throw new AbstractNotFoundException("Entity not found") {
            };
        }

        @GetMapping("/forbidden")
        public void throwForbiddenException() {
            throw new ForbiddenException();
        }

        @GetMapping("/unknown")
        public void throwUnknownException() {
            throw new RuntimeException("Unknown error");
        }
    }

    @Test
    void handleValidationException() throws Exception {
        mockMvc.perform(get("/test/invalidData"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid data in request."))
                .andExpect(jsonPath("$.description").value("Invalid data provided"));
    }

    @Test
    void handleItemUnavailableException() throws Exception {
        mockMvc.perform(get("/test/itemUnavailable"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Item unavailable."))
                .andExpect(jsonPath("$.description").value("Item with id=1 unavailable"));
    }

    @Test
    void handleUnableToChangeBookingStatusException() throws Exception {
        mockMvc.perform(get("/test/unableToChangeBookingStatus"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Unable to change status."))
                .andExpect(jsonPath("$.description").value("Unable to change status."));
    }

    @Test
    void handleAddCommentForbiddenException() throws Exception {
        mockMvc.perform(get("/test/addCommentForbidden"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Not possible to add a comment."))
                .andExpect(jsonPath("$.description").value("Comment creation forbidden."));
    }

    @Test
    void handleConstraintViolationException() throws Exception {
        mockMvc.perform(get("/test/constraintViolation"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid request"))
                .andExpect(jsonPath("$.description").value("Invalid request"));
    }

    @Test
    void handleNotFoundException() throws Exception {
        mockMvc.perform(get("/test/notFound"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Entity not found"))
                .andExpect(jsonPath("$.description").value("Entity not found"));
    }

    @Test
    void handleForbiddenException() throws Exception {
        mockMvc.perform(get("/test/forbidden"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Access denied"))
                .andExpect(jsonPath("$.description").value("Action forbidden"));
    }

    @Test
    void handleUnknownException() throws Exception {
        mockMvc.perform(get("/test/unknown"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Unexpected error"))
                .andExpect(jsonPath("$.description").value("Unknown error"));
    }
}
