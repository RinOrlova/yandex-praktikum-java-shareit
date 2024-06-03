package ru.practicum.shareit.request.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ItemRequestRepositoryTest {

    @Autowired
    private ItemRequestRepository itemRequestRepository;

    @Test
    public void testGetItemRequestEntitiesByRequestorId() {
        Long requestorId = 1L;
        List<ItemRequestEntity> entities = itemRequestRepository.getItemRequestEntitiesByRequestor_Id(requestorId);

        assertThat(entities).isNotNull();
        assertThat(entities).allMatch(entity -> entity.getRequestor().getId().equals(requestorId));
    }

    @Test
    public void testFindAllExcludingRequestorId() {
        Long requestorId = 1L;
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ItemRequestEntity> page = itemRequestRepository.findAllExcludingRequestorId(requestorId, pageRequest);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).allMatch(entity -> !entity.getRequestor().getId().equals(requestorId));
    }
}
