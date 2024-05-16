package ru.practicum.shareit.item.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.user.data.UserEntity;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

    List<ItemEntity> getAllByUserEntity(UserEntity userEntity);

    @Query("SELECT COUNT(b) > 0 " +
            "FROM BookingEntity b " +
            "WHERE b.item.id = :itemId " +
            "AND b.booker.id = :userId " +
            "AND b.end < CURRENT_TIMESTAMP")
    boolean existsByItemIdAndUserIdWithPastBookings(@Param("itemId") Long itemId, @Param("userId") Long userId);

}
