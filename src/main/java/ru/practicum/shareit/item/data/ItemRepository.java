package ru.practicum.shareit.item.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.data.UserEntity;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

    List<ItemEntity> getAllByUserDto(UserEntity userDto);

}
